(function() {
    'use strict';
    const application = angular.module("application", ['ngMaterial']);
    application.controller('applicationCtrl', ['$scope', function(scope) {
        scope.toggleGridList = function(newValue) {
            if (newValue === "gridView") {
                scope.value = 'gridView';
                scope.layout = 'row';
            } else {
                scope.value = 'listView';
                scope.layout = 'column';
            }
        };
        scope.onInit = function() {
            scope.toggleGridList('listView');
        };
    }]);
})();

