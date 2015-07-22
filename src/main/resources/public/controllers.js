'use strict';

var healthApp = angular.module('healthApp', []);


healthApp.controller('HealthCtrl', function ($scope, $http, $interval) {

    $scope.getPanelHealthClass = function (status) {
        if (status == "UP")
            return "panel-success"
        else if (status == "UNKNOWN")
            return "panel-warning";
        else
            return "panel-danger";
    }

    $scope.getBackgroundHealthClass = function (status) {
        if (status == "UP")
            return "";
        else if (status == "UNKNOWN")
            return "bg-warning";
        else
            return "bg-danger";
    }

    $scope.getGlyphClass = function (status) {
        if (status == "UP")
        // do not highlight
            return "glyphicon-ok-circle";
        else if (status == "UNKNOWN")
            return "glyphicon-question-sign";
        else
            return "glyphicon-exclamation-sign";
    }


    $scope.updateDashboard = function () {
        $http.get('/healthstatus').success(function (data) {
            $scope.applications = data;
            $scope.lastUpdated = new Date().toLocaleString();
        })
    }


    $interval(function () {
        $scope.updateDashboard();
    }, 1000);

    $scope.updateDashboard();


});