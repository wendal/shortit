// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

var req = new XMLHttpRequest();
req.open(
    "GET",
    "http://nutz.cn/api/create/url?data="+location.href,
    true);
req.onload = function () {
    var j = req.responseText.split("'");
    if (j[1] == "ok") {
		alert("http://nutz.cn/" + j[5]);
	} else{
		alert("FAIL");
	}
    return;
};
req.send(null);

