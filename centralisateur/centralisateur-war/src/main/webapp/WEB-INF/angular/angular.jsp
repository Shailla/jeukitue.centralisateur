<!doctype html>
<html ng-app>
    <head>
        <title>Spring MVC + AngularJS Demo</title>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
        <script>
        function Hello($scope, $http) {
            $http.get('http://localhost:8089/centralisateur-war/angular/essai.do').
                success(function(dto) {
                    $scope.dto = dto;
                });
        }
        </script>
    </head>
    <body>
        <div ng-controller="Hello">
            <h2>Essai Spring MVC + AngularJS</h2>
            <p>Var : {{dto.var}}</p>
        </div>
    </body>
</html>
