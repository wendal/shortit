package main

import (
	"database/sql"
	"database/sql/driver"
	"flag"
	"fmt"
	_ "github.com/mattn/go-sqlite3"
	"html"
	"log"
	"net/http"
	"regexp"
	"strconv"
	"strings"
)

var (
	port   = flag.Int("port", 80, "port for listen")
	dbType = flag.String("dbType", "sqlite3", "db type")
	dbUrl  = flag.String("dbUrl", "sdata.db", "db url")
	base   = flag.String("base", "../WebContent", "static file baseDir")
	driver driver.Driver
)

var sdataPatten, _ = regexp.Compile("^/[1234567890abcdef]+$")
var staticFile http.Handler

func main() {
	flag.Parse()
	staticFile = http.FileServer(http.Dir(*base))

	db, e := sql.Open(*dbType, *dbUrl)
	if e != nil {
		log.Fatal(e)
	}
	_, e = db.Query("select 1")
	if e != nil {
		log.Fatal(e)
	}
	_, e = db.Query("select * from t_sdata where 1 != 1")
	if e != nil {
		_, e = db.Exec("create table t_sdata(id integer primary key autoincrement,data varchar(2048), tp integer, ct default (datetime(CURRENT_TIMESTAMP,'localtime')), up default (datetime(CURRENT_TIMESTAMP,'localtime')))")
		if e != nil {
			log.Fatal(e)
		}
	}
	driver = db.Driver()

	http.HandleFunc("/api/create", Create)
	http.HandleFunc("/api/create/bin", CreateBin)
	http.HandleFunc("/api/query", Query)
	http.HandleFunc("/", SData)

	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", *port), nil))
}

func SData(w http.ResponseWriter, r *http.Request) {
	if !sdataPatten.MatchString(r.URL.Path) {
		log.Print("static file path=" + r.URL.Path)
		staticFile.ServeHTTP(w, r)
		return
	}
	id, _ := strconv.ParseInt(r.URL.Path[1:], 16, 32)
	data, _type := queryById(id)
	if data == "" {
		http.NotFound(w, r)
		return
	}
	if _type == 0 {
		http.Redirect(w, r, data, 302)
	} else {
		fmt.Fprintf(w, "<html><head><title>TXT</title></head><body>%s</body></html>", html.EscapeString(data))
	}
}

func Create(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()

	data := strings.Trim(r.FormValue("data"), " \n\r")
	if data == "" {
		fmt.Fprintf(w, "{'ok':false, 'msg': 'err.data_emtry'}")
		return
	}
	if len(data) > 2047 {
		fmt.Fprintf(w, "{'ok':false, 'msg': 'err.data_too_long'}")
		return
	}
	_type := 1
	if strings.HasPrefix(data, "http://") || strings.HasPrefix(data, "https://") || strings.HasPrefix(data, "ftp://") {
		_type = 0
	}
	id := queryByDataAndType(data, _type)
	if id >= 0 {
		fmt.Fprintf(w, "{'ok':true, 'code': '%s'}", strconv.FormatInt(id, 16))
		return
	}
	conn, _ := driver.Open(*dbUrl)
	defer conn.Close()
	res, err := conn.Exec("insert into t_sdata (data,tp) values(?,?)", data, _type)
	if err != nil {
		log.Print(err)
		id = queryByDataAndType(data, _type)
	} else {
		id, _ = res.LastInsertId()
	}
	fmt.Fprintf(w, "{'ok':true, 'code': '%s'}", strconv.FormatInt(id, 16))
}

func CreateBin(w http.ResponseWriter, r *http.Request) {

}

func Query(w http.ResponseWriter, r *http.Request) {

}

func queryById(id int64) (data string, _type int) {
	conn, _ := driver.Open(*dbUrl)
	defer conn.Close()
	row := conn.QueryRow("select data,tp from t_sdata where id=?", id)

	row.Scan(&data, &_type)
	return
}

func queryByDataAndType(data string, _type int) (id int64) {
	conn, _ := driver.Open(*dbUrl)
	defer conn.Close()
	row := conn.QueryRow("select id from t_sdata where data=? and tp=?", data, _type)

	if row.Scan(&id) != nil {
		id = -1
	}
	return

}
