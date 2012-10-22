package cn.nutz.shortit.bean;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

// 表名
@Table("t_sdata") 
// 索引设置, data+type唯一
@TableIndexes({@Index(name="data_type",fields={"data", "type"},unique=true)})
/**
 * 数据记录
 * @author wendal
 */
public class DataEntry {

	/**自增id TODO oracle的形式*/
	@Id
	public long id;
	/**数据字段,2k足够了吧?!*/
	@Column
	@ColDefine(type=ColType.VARCHAR, width=2048)
	public String data;
	/**数据类型*/
	@Column("tp")
	public int type;
	/**创建时间*/
	@Column("ct")
	public Timestamp creatTime;
	/**更新时间*/
	@Column("ut")
	public Timestamp updateTime;
	
	// 懒得封装了,全部字段public

	/**文件名*/
	public String filename() {
		return data.substring(0, data.lastIndexOf(','));
	}

	/**文件ID*/
	public String fid() {
		return data.substring(data.lastIndexOf(',') + 1);
	}

	public enum DataType{
		URL(0),
		STRING(1),
		FILE(2);

		private int value = 0;

		DataType(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}
}
