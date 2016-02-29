define("utils/detector",["mobiledetect"],function(t){return new t(navigator.userAgent)}),define("collections/topics",["backbone"],function(t){return t.Collection.extend({initialize:function(){}})}),define("collections/locations",["backbone"],function(t){return t.Collection.extend({initialize:function(){}})}),define("collections/dates",["backbone"],function(t){return t.Collection.extend({initialize:function(){}})}),define("utils/color",["jquerycolor"],function(){return{random:function(){var t=12*Math.floor(30*Math.random());return $.Color({hue:t,saturation:.9,lightness:.8,alpha:1}).toHexString()},blue:"000080",yellow:"FFD700",gray:"708090",orange:"A0522D",green:"228B22",purple:"800080",white:"FFFFFF"}}),define("models/article",["utils/color","underscore","backbone"],function(t){return Backbone.Model.extend({initialize:function(e){var o,n="black";switch(e.rootTopic){case"Business":n="white",o=t.blue;break;case"Disasters":o=t.yellow;break;case"Politics":n="white",o=t.gray;break;case"Crime":o=t.orange;break;case"Sports":o=t.green;break;case"Science & Education":o=t.purple;break;default:o=t.white}this.set("color",n),this.set("colorHex",o)}})}),define("utils/buildurl",[],function(){return function(t,e){var o=e.shift();return o&&(t+="?"+o,_.each(e,function(e){t+="&"+e})),t}}),define("utils/spiderfy",[],function(){return function(t,e){if(!e||t.length<2)return t;var o=void 0,n=void 0,i=void 0,s=void 0,a={};_.each(t,function(t){var r=t.x+","+t.x;a[r]||(a[r]=[]),a[r].push(t),e||((!o||t.x<o)&&(o=t.x),(!n||t.x>n)&&(n=t.x),(!i||t.y>i)&&(i=t.y),(!s||t.y<s)&&(s=t.y))});var r,c;if(e)r=(e.right-e.left)/10,c=(e.top-e.bottom)/10;else var r=.5,c=.5;return _.each(a,function(t){if(t.length>1)for(var e=0;e<t.length;e++){var o=t[e];o.x-=r*Math.cos(2*Math.PI*e/t.length),o.y+=c*Math.sin(2*Math.PI*e/t.length)}}),t}}),define("collections/news",["collections/topics","collections/locations","collections/dates","models/article","utils/buildurl","utils/spiderfy","utils/detector","underscore","backbone"],function(t,e,o,n,i,s,a){return Backbone.Collection.extend({model:n,initialize:function(t,e){this.rootUrl=t,e.topic&&(this.topic=e.topic),e.loc&&(this.loc=e.loc),e.date&&(this.date=e.date),e.mapBounds&&(this.mapBounds=e.mapBounds),e.search&&(this.search=e.search),this.fetches=0,this.on("request",_.bind(function(){this.fetches++},this)),this.fetch()},parse:function(n){if(this.fetches--,n.topics&&(this.topics=new t(n.topics.children)),n.locations&&(this.locations=new e(n.locations.children)),n.dates&&(this.dates=new o(n.dates.children)),s(n.news,this.mapBounds),this.topic||this.loc||this.date||this.mapBounds||this.search){var a=[];this.mapBounds&&a.push("left="+this.mapBounds.left,"right="+this.mapBounds.right,"top="+this.mapBounds.top,"bottom="+this.mapBounds.bottom),this.search&&a.push("search="+this.search),require("router").navigateTo(i((this.topic?"topic/"+this.topic:"")+(this.topic&&this.loc?"/":"")+(this.loc?"location/"+this.loc:"")+((this.topic||this.loc)&&this.date?"/":"")+(this.date?"date/"+this.date:""),a))}return n.news},url:function(){console.log("fetching map");var t=[];return this.mapBounds&&t.push("left="+this.mapBounds.left,"right="+this.mapBounds.right,"top="+this.mapBounds.top,"bottom="+this.mapBounds.bottom),this.localLat&&this.localLong&&t.push("local-lat="+this.localLat,"local-long="+this.localLong),this.topic&&t.push("topic="+encodeURI(this.topic)),this.loc&&t.push("location="+encodeURI(this.loc)),this.date&&t.push("date="+encodeURI(this.date)),this.search&&t.push("keywords="+this.search),a.phone()&&t.push("isMobile=true"),i(this.rootUrl+"news",t)}})}),define("utils/css",["utils/detector","jquery"],function(t){var e=$("head");return{load:function(o){e.append('<link rel="stylesheet" type="text/css" href="css/'+(t.phone()?"mobile/":"")+o+'.css">')}}}),define("components/options",["react","utils/css"],function(t,e){return t.createClass({getInitialState:function(){return e.load("options"),{}},render:function(){return t.createElement("div",{id:"mr-options-bucket"},this.props.loading?t.createElement("span",{className:"header"}," MAPREPORT"):t.createElement("div",{id:"mr-options"},t.createElement("div",{className:"mr-option mr-topic-option",onClick:this.toggleMenu},t.createElement("span",{className:"mr-option-cell"},t.createElement("span",null,"TOPIC: ",this.props.news.topic||"All Topics"),this.props.news.topics.models.map(function(e){return t.createElement("div",{onClick:this.updateTopic},e.get("node"))}.bind(this)))),t.createElement("div",{className:"mr-option mr-location-option",onClick:this.toggleMenu},t.createElement("span",{className:"mr-option-cell"},t.createElement("span",null,"LOCATION: ",this.props.news.loc||"All Locations"),this.props.news.locations.models.map(function(e){return t.createElement("div",{onClick:this.updateLocation},e.get("node"))}.bind(this)))),t.createElement("div",{className:"mr-option mr-time-option",onClick:this.toggleMenu},t.createElement("span",{className:"mr-option-cell"},t.createElement("span",null,"TIME: ",this.props.news.date||"All Time"),this.props.news.dates.models.map(function(e){return t.createElement("div",{onClick:this.updateTime},e.get("node"))}.bind(this))))))},toggleMenu:function(t){$(t.currentTarget).toggleClass("mr-expanded")},updateTopic:function(t){this.props.news.topic=$(t.target).html(),this.props.news.fetch()},updateLocation:function(t){this.props.news.loc=$(t.target).html(),this.props.news.mapBounds=null,this.props.news.fetch()},updateTime:function(t){this.props.news.date=$(t.target).html(),this.props.news.fetch()}})}),define("components/map",["react","utils/css"],function(t,e){return t.createClass({getInitialState:function(){return window.mapComponent=this,window.initMap=function(){var t=37.7576793,e=-122.5076404;mapComponent.map=new google.maps.Map(document.getElementById("mr-map"),{center:{lat:t,lng:e},streetViewControl:!1,mapTypeControl:!1,zoom:8}),google.maps.event.addListener(mapComponent.map,"zoom_changed",function(){var t=google.maps.event.addListener(mapComponent.map,"bounds_changed",function(e){this.getZoom()<1&&(mapComponent.readjustingZoom=!0,this.setZoom(1),mapComponent.readjustingZoom=!1),google.maps.event.removeListener(t)});mapComponent.props.loading||mapComponent.readjustingZoom||mapComponent.fittingBounds||mapComponent.fetchDataForNewBounds()}),google.maps.event.addListener(mapComponent.map,"dragend",mapComponent.fetchDataForNewBounds),navigator.geolocation&&navigator.geolocation.getCurrentPosition(function(t){mapComponent.curlat=t.coords.latitude,mapComponent.curlong=t.coords.longitude})},require(["gmaps"]),e.load("map"),{}},render:function(){return t.createElement("div",{id:"mr-map-bucket"},t.createElement("div",{id:"mr-map"}),t.createElement("div",{id:"mr-search"},t.createElement("input",{id:"mr-search-input"}),t.createElement("span",{id:"mr-search-action"},"Search"),t.createElement("span",{id:"mr-search-clear"},"X")),t.createElement("img",{id:"mr-curloc-button",src:"images/curloc.png"}))},componentDidMount:function(){var t=$("#mr-search-input");this.searchInput=t,t.keyup(function(e){13==e.keyCode&&(this.props.news.search=t.val(),this.props.news.fetch())}.bind(this)),$("#mr-search-action").show().click(function(){this.props.news.search=t.val(),this.props.news.fetch()}.bind(this)),$("#mr-search-clear").show().click(function(){this.props.news.search=null,t.val(""),this.props.news.fetch()}.bind(this)),$("#mr-curloc-button").show().click(function(){this.props.news.loc="Local",this.props.news.localLat=this.curlat,this.props.news.localLong=this.curlong,this.props.news.fetch()}.bind(this))},componentDidUpdate:function(){if(!this.props.loading){this.props.search&&this.searchInput.val(this.props.search),this.markers&&this.markers.length&&this.markers.map(function(t){t.setMap(null)}.bind(this)),this.markers=[];var t=new google.maps.LatLngBounds;this.props.news.models.map(function(e){if(e.get("isMapShow")){var o=new google.maps.Marker({position:new google.maps.LatLng(e.get("y"),e.get("x")),map:this.map,icon:e.get("icon"),title:e.get("label")});t.extend(o.getPosition()),o.addListener("click",function(){new google.maps.InfoWindow({content:'<a href="'+e.get("url")+'" target="_blank">'+e.get("dateTime")+" @ "+e.get("address")+": "+e.get("label")+"</a>"}).open(this.map,o)}.bind(this)),this.markers.push(o)}}.bind(this)),!this.mapMoved&&this.props.news.models.length&&(this.fittingBounds=!0,this.map.fitBounds(t),this.fittingBounds=!1),this.mapMoved=!1}},fetchDataForNewBounds:function(){this.mapMoved=!0;var t=this.map.getBounds(),e=t.getNorthEast(),o=t.getSouthWest();this.props.news.mapBounds={left:o.lng(),right:e.lng(),top:e.lat(),bottom:o.lat()},this.props.news.loc=void 0,this.props.news.fetch()}})}),define("components/report",["react","utils/css","utils/color"],function(t,e,o){return t.createClass({getInitialState:function(){return e.load("report"),{}},render:function(){return t.createElement("div",{id:"mr-report"},this.props.news.models.map(function(e){var o={color:e.get("color"),backgroundColor:"#"+e.get("colorHex")};return t.createElement("div",{className:"mr-report-article",style:o,onClick:this.goToArticle.bind(this,e)},t.createElement("img",{src:e.get("icon")}),e.get("video")?t.createElement("img",{className:"mr-video",src:"http://www.mapreport.com/images/common/video.jpg"}):"",t.createElement("p",null,e.get("dateTime"),": ",e.get("label")))}.bind(this)))},goToArticle:function(t,e){open(t.get($(e.target).is(".mr-video")?"video":"url"),"_blank")}})}),define("components/page",["react","utils/detector","collections/news","components/options","components/map","components/report"],function(t,e,o,n,i,s){return t.createClass({getInitialState:function(){return window.page=this,new o("http://"+router.rootDomain+router.pathPrefix,router.settings).on("sync",function(t){this.setState({loading:t.fetches,news:t,latitude:37.759753,longitude:-122.50232699999998,search:t.search})}.bind(this)).on("request",function(){this.setState({loading:!0})}.bind(this)),{loading:!0}},render:function(){return t.createElement("div",{id:"mr-buckets",className:e.phone()?"mobile":"desktop"},t.createElement(n,{news:this.state.news,loading:this.state.loading}),t.createElement(i,{news:this.state.news,latitude:this.state.latitude,longitude:this.state.longitude,loading:this.state.loading,search:this.state.search}),t.createElement("div",{id:"mr-report-bucket"},this.state.loading?"LOADER":t.createElement(s,{news:this.state.news})))}})}),define("router",["react","components/page","backbone"],function(t,e){return new(Backbone.Router.extend({initialize:function(){window.router=this;var t="pushState"in history;Backbone.history.start({pushState:t,hashChange:t})},routes:{"*path":"homePage"},homePage:function(o){this.rootDomain=document.domain+":8080",this.pathPrefix=-1===document.domain.indexOf("amazon")?"/mapreport-stable/":"/mapreport/";var n,i=location.pathname.split("/"),s=i.indexOf("topic")+1;s&&s<i.length&&(n=i[s]);var a,r=i.indexOf("location")+1;r&&r<i.length&&(a=i[r]);var c,l=i.indexOf("date")+1;l&&l<i.length&&(c=i[l]);var p,h,u,d,m;_.each(location.search.slice(1).split("&"),function(t){var e=t.split("=");"left"===e[0]?p=e[1]:"right"===e[0]?h=e[1]:"top"===e[0]?u=e[1]:"bottom"===e[0]?d=e[1]:"search"===e[0]&&(m=e[1])}),this.settings={topic:n,loc:a,date:c},p&&h&&u&&d&&(this.settings.mapBounds={left:p,right:h,top:u,bottom:d}),m&&(this.settings.search=m),"undefined"==typeof page?t.render(t.createElement(e),$("body")[0]):page.getInitialState()},redirectTo:function(t){location.href=t},navigateTo:function(t){this.navigate(this.pathPrefix+t)}}))}),requirejs.config({paths:{jquery:"//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min",jquerycolor:"//cdnjs.cloudflare.com/ajax/libs/jquery-color/2.1.2/jquery.color.min",underscore:"//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min",backbone:"//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min",react:"//cdnjs.cloudflare.com/ajax/libs/react/0.13.3/react.min",mobiledetect:"//cdnjs.cloudflare.com/ajax/libs/mobile-detect/0.4.0/mobile-detect.min",gmaps:"//maps.googleapis.com/maps/api/js?key=AIzaSyCH89LosFbZ6Inmy5T8yCL0ao54qf2htCo&callback=initMap"},shim:{jquerycolor:{deps:["jquery"],exports:"jquerycolor"}}}),requirejs(["router"],function(t){}),define("mapreport",function(){});