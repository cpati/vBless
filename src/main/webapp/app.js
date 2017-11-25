'use strict';

var myApp = angular.module('myApp', [ 'ngRoute' ]);

myApp.config(function($routeProvider, $qProvider) {
	$routeProvider.when("/", {
		templateUrl : "home.html",
		controller : "homeController"
	}).when("/list", {
		templateUrl : "campaignList.html",
		controller : "campaignListController"
	}).when("/userProfile", {
		templateUrl : "userProfile.html",
		controller : "userProfileController"
	}).when("/share", {
		templateUrl : "share.html",
		controller : "shareController"
	}).when("/about", {
		templateUrl : "about.html"

			//added this for create campaign UI and java backend
	}).when("/createCampaign", {
		templateUrl : "createCampaign.html",
		controller : "campaignController"
	});

	$qProvider.errorOnUnhandledRejections(false);
});

myApp.controller('userProfileController', function($scope, $http, httpPost, $location) {
	console.log("userProfileController");
	$scope.userid="1";
	

	$http.get("/vBless/vBless/getCampaignUser/" + $scope.userid).then(
	function(response) {
		console.log("getUser:" + $scope.userid);
		console.log(response.data);
		$scope.firstname = response.data.firstname;
		$scope.lastname = response.data.lastname;
		$scope.email = response.data.email;
		$scope.phone = response.data.phone;
		$scope.paymentinfo = response.data.paymentinfo;
	});
	
	$scope.doSave = function(){
	      
	       var url = "http://"+$location.$$host+':'+$location.$$port+"/vBless/vBless/updateCampaignUser";
	       
	       var data = new FormData();
	       data.append('userId',$scope.userid);
	       data.append('firstname',$scope.firstname);
	       data.append('lastname',$scope.lastname);
	       data.append('phone', $scope.phone);
	       data.append('email',$scope.email);
	       data.append('paymentinfo',$scope.paymentinfo);
	       
	       var config = {
	    	   	transformRequest: angular.identity,
	    	   	transformResponse: angular.identity,
		   		headers : {
		   			'Content-Type': undefined
		   	    }
	       }
	       
	       $http.post(url, data, config).then(function (response) {
	    	   if(response.data != "FAIL")  {
	    		   console.log("Campaign user details saved ");
	    		   $location.url("list");
	    	   }
			});
	    };
	    
	$scope.doCancel = function(){
    	console.log("Edit campaignUser cancelled")
    	$location.url("list");
    }

});

myApp.controller('logoutController', function($scope, $http, $location) {
	console.log("logoutController");
});

myApp.controller('shareController', function($scope, $http, $location) {
	console.log("shareController");
	var id = 10;
	$scope.title = "lets hardcode title, will fetch from campaign later, For details visit";
	$scope.shareurl="http://"+$location.$$host+':'+$location.$$port+"/vBless";
//	$scope.shareurl="http://"+$location.$$host+':'+$location.$$port+"/vBless/campaignDetails/" +id;
	
});


/* Campaign Controller Implementation Start */

myApp.controller('campaignController',function($scope, httpPost, operation, $location, $http) {
					console.log("campaignController initialized");
	if(operation.getType()=="Add"){
			$scope.activeStatus=true;
			$scope.disableStatus=false;
			$scope.editMode=true;
	}				
	$scope.saveFormData = function() {
		
			var dataString = 'projectTitle='+ $scope.projectTitle + '&';
			dataString += 'initDesc=' + $scope.initiativeDesc	+ '&';
			dataString += 'shortBlurb=' + $scope.shortBlurb + '&';
			dataString += 'category=' + $scope.category + '&';
			dataString += 'fGoal=' + $scope.fGoal + '&';
			dataString += 'city=' + $scope.city + '&';
			dataString += 'campDurDay='	+ $scope.campaignDurationDay + '&';
			dataString += 'country=' + $scope.country + '&';
			dataString+='createdDate='+$scope.createdDate + '&';
			dataString += 'type=' + operation.getType();


			$http.post('createCampaign/save?' + dataString).then(function(response) {
				if(response.data){
					/*$scope.projectTitle = "";
					$scope.initiativeDesc = "";
					$scope.shortBlurb = "";
					$scope.category = ""
					$scope.city = "";
					$scope.fGoal = "";
					$scope.campaignDurationDay = "";
					$scope.country = "";
					document.querySelector('img').src = "";
					document.querySelector('img').style.display = "none";
					document.querySelector('input[type=file]').value = "";*/
					$location.path("/list");
					alert('saved');
				}
			});	
		
	};
$http.get("createCampaign/getCamapignById/"	+ operation.getId()).then(function(response) {
	
	if(response.data==undefined || response.data==null || response.data==""){
		alert("Could Not Find Campaign. Please give correct criteria");
	}else{
	$scope.projectTitle = response.data.campaignTitle != undefined ? response.data.campaignTitle: "";
	$scope.initiativeDesc = response.data.campaignDescription != undefined ? response.data.campaignDescription: "";
	$scope.shortBlurb = response.data.blurb != undefined ? response.data.blurb	: "";
	$scope.category = response.data.category != undefined ? response.data.category	: "";
	$scope.city = response.data.city != undefined ? response.data.city: "";
	$scope.fGoal = response.data.goal != undefined ? response.data.goal	: "";
	$scope.campaignDurationDay = response.data.duration != undefined ? response.data.duration: "";
	$scope.country = response.data.country != undefined ? response.data.country	: "";
	$scope.activeStatus=response.data.active==="Y";
	$scope.disableStatus=response.data.active==="N";
	if($scope.activeStatus=='Y'){
		$scope.createdDate=new Date(response.data.createDate);
	}else{
		$scope.createdDate=response.data.createDate;
	}
	if($scope.disableStatus){
		$scope.editMode=false;
	}else{
		$scope.editMode=true;
	}
	
	document.querySelector('img').style.display="block";
	document.querySelector('img').src='data:image/png;base64,'+response.data.imageBlob;
	document.querySelector('input[type=file]').value = "image.jpg";
	}
});						
//To set the image parameter						
$scope.uploadPhoto = function(id) {
	// var photo = 'photo='+file;
	var fd = new FormData();
	fd.append('file', document.querySelector('input[type=file]').files[0]);
	httpPost.postCall(fd, 'createCampaign/uploadPhoto');
};						
					
});

function previewFile() {
	var preview = document.querySelector('img'); // selects the query named
	// img
	var file = document.querySelector('input[type=file]').files[0]; // sames as
	// here
	var reader = new FileReader();

	reader.onloadend = function() {
		preview.src = reader.result;
		preview.style.display = "block"
	}

	if (file) {
		reader.readAsDataURL(file); // reads the data as a URL
	} else {
		preview.src = "";
	}
};

myApp.service('httpPost', [ '$http', function($http) {
	this.postCall = function(fd, postUrl) {
		console.log("postCall");
		$http.post(postUrl, fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).then(function(response) {
			console.log("response")
		})
	}
} ]);
myApp.service('operation', function() {
	var type = "Add";
	var id = null;

	return {
		getType : function() {
			return type;
		},
		setType : function(value) {
			type = value;
		},
		getId : function() {
			return type;
		},
		setId : function(value) {
			type = value;
		}
	}
});

/* Remove Temp Controller. Implementation should provide by respective page owner*/

myApp.controller('campaignListController', function($scope, httpPost,
		operation, $location, $http, $rootScope) {
	console.log("campaignListController initialized");
	$scope.updateFormData = function() {
		operation.setType('update');		//Mandatory
		operation.setId($scope.cid);		//Mandatory
		$location.path("/createCampaign");	//Mandatory
	};
	
	function campaigns(id,title,createdBy,initDesc,image){
		this.id=id;
		this.title=title;
		this.createdBy=createdBy;
		this.initDesc=initDesc;
		this.image=image;
	}
	
	$scope.campaigns=[];
	
	$http.get("createCampaign/top").then(function(data){
		console.log(data.data);
		for (var row of data.data){
			$scope.campaigns.push(new campaigns(row.campaignId,row.campaignTitle,"chida",row.goal,row.imageBlob));
		}
	});
	
	
	$scope.heroCardCss=function(image){
		var imgCss="background-image: url(data:image/png;base64,"+image+");"+
	   			   "height: 50%;background-position: center;background-repeat: no-repeat;background-size: cover;position: relative;";
		return imgCss;
	}
});

myApp.controller('homeController', function($scope, httpPost,
		operation, $location, $http, $rootScope) {
	console.log("homeController initialized");
	
	function campaigns(id,title,createdBy,initDesc,image){
		this.id=id;
		this.title=title;
		this.createdBy=createdBy;
		this.initDesc=initDesc;
		this.image=image;
	}
	
	$scope.campaigns=[];
	
	$http.get("createCampaign/top").then(function(data){
		console.log(data.data);
		for (var row of data.data){
			$scope.campaigns.push(new campaigns(row.campaignId,row.campaignTitle,"chida",row.goal,row.imageBlob));
		}
		$scope.campaignsFirst=$scope.campaigns[0];
		$scope.campaignsRest=$scope.campaigns.slice(1,$scope.campaigns.length);
	});
	
	$scope.heroCardCss=function(image){
		//var imgCss="background-image: "+imageURL+";"+
		//			"height: 50%;background-position: center;background-repeat: no-repeat;background-size: cover;position: relative;";
		//console.log(imgCss);
		var imgCss="background-image: url(data:image/png;base64,"+image+");"+
	   			   "height: 70%;background-position: center;background-repeat: no-repeat;background-size: cover;position: relative;";
		return imgCss;
	}

});

/* Campaign Controller Implementation End */

