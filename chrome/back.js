
			/* Init */
			var textarea;
			
			/* Init */
			function init() {
				textarea = document.getElementById('url');
				
				chrome.browserAction.setBadgeBackgroundColor(
		  		{
		  			color: [100, 150, 250, 200]
		  		}
		  	);
		  	
		  	chrome.browserAction.onClicked.addListener(req);
			}
			
			/* Copy */
			function copy(obj) {
				textarea.value = obj['id'];					
				textarea.select();
				document.execCommand('Copy');
			}
			
			/* OK */
			function ok() {
				chrome.browserAction.setBadgeText(
					{
						text: 'OK'
					}
				);
				
				setTimeout(
					function() {
						chrome.browserAction.setBadgeText(
		  				{
		  					text: ''
		  				}
		  			);
					},
					3000
				);
			}
			
			/* Request */
			function req(tab) {
				/* Leer? */
				if ( !tab.url ) return;
				
				/* Init */
				
				/* Request */
				var	xhr = new XMLHttpRequest();
				xhr.open('POST', 'http://nutz.cn/api/create/url?data='+encodeURIComponent(tab.url), false);
				
				/* Responce */
				xhr.onreadystatechange = function() {
				  if (xhr.readyState == 4) {
				    var obj = JSON.parse(xhr.responseText);

				    if ( obj.ok) {
				    		obj['id'] = "http://nutz.cn/" + obj.code;
							copy(obj);
							ok();
					}
				  }
				};
	
				/* Send */
				xhr.send(null);
			}
			
			/* Init */
			init();