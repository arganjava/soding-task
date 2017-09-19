<html ng-app="task-soding">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Task</title>
    <script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/angularjs/1.6.4/angular.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
</head>
<body ng-controller="TaskController">
${message}
<div class="panel panel-default">
    <div class="panel-heading"><h3>Task</h3></div>
    <div class="panel-body"><table class="table col-sm-10" id="tblTask">
        <thead>
        <th>Name</th>
        <th>Description</th>
        <th>Create Date</th>
        <th>Update Date</th>
        <th>Action</th>
        </thead>
        <thead>
        <th><input ng-model="name"></th>
        <th><input ng-model="description"></th>
        <th></th>
        <th></th>
        <th>
            <button ng-if="state != 'edit' " ng-click="saveTask()">Save</button>
            <button ng-if="state == 'edit' " ng-click="updateTask()">Update</button>
            <button ng-if="state == 'edit' " ng-click="cancelUpdate()">Cancel</button>
        </th>
        </thead>
        <tbody>
        <tr ng-repeat="task in taskData.content | limitTo:taskData.size">
            <td> {{task.name}}</td>
            <td> {{task.description}}</td>
            <td>{{task.dateCreated}}</td>
            <td> {{task.dateUpdated}}</td>
            <td>
                <button class="btn-primary" ng-click="edit(task)">Edit</button>
                &nbsp;
                <button class="btn-danger" ng-click="delete(task.id)">Delete</button>
            </td>
        </tr>
        </tbody>

    </table>
        <div style="text-align: center" ng-if="taskData.content.length > 0">
            <label>Page {{taskData.number+1}} of {{taskData.totalPages}}</label>
            <label> and </label>
            <label>Total Task {{taskData.totalElements}}</label>
            <button class="btn-primary" ng-click="prev()" ng-show="taskData.number != 0">Prev</button>
            <button class="btn-primary" ng-click="next()" ng-show="taskData.totalPages > param.pageNumber+1">Next</button>
        </div>

        <div style="text-align: center" ng-if="taskData.content.length == 0">
            <button class="btn-primary" ng-click="refresh()">Refresh</button>
        </div></div>
</div>



</body>
<script type="text/javascript">

    angular.module("task-soding", [])

            .controller("TaskController", function ($scope, $http, $filter) {
                $scope.param = {
                    pageNumber: 0
                }

                $scope.taskData = {
                    content: [],
                    totalElements: 0,
                    totalPages: 0,
                    size: 0
                }

                $scope.state = null;
                var getAllTask = '<c:url value="/getAll"/>';
                var createTaskUrl = '<c:url value="/create"/>';
                var updateTaskUrl = '<c:url value="/update"/>';
                var deleteTaskUrl = '<c:url value="/delete"/>';


                $scope.reset = function () {
                    $scope.name = '';
                    $scope.description = '';
                    $scope.state = null;
                }

                $scope.refresh = function () {
                    $scope.reset();
                    $scope.param = {
                        pageNumber: 0
                    }
                    $scope.loadInit();
                }


                $scope.loadInit = function () {
                    $http({
                        method: 'GET',
                        url: getAllTask,
                        params: $scope.param
                    }).then(function (res) {
                        $scope.taskData = res.data;
                        angular.forEach($scope.taskData.content, function (task) {
                            task.dateCreated = task.dateCreated == null ? '' : new Date(task.dateCreated).toLocaleString()
                            task.dateUpdated = task.dateUpdated == null ? '' : new Date(task.dateUpdated).toLocaleString()
                        }.bind(this));
                    });
                }
                $scope.loadInit();

                $scope.next = function () {
                    $scope.param.pageNumber = $scope.param.pageNumber + 1;
                    $scope.loadInit();
                }

                $scope.prev = function () {
                    $scope.param.pageNumber = $scope.param.pageNumber - 1;
                    $scope.loadInit();
                }


                $scope.edit = function (task) {
                    $scope.name = task.name;
                    $scope.description = task.description;
                    $scope.id = task.id;
                    $scope.state = 'edit';

                }

                $scope.delete = function (id) {

                    $http({
                        method: 'POST',
                        url: deleteTaskUrl,
                        params: {id: id}
                    }).then(
                            function successCallback(res) {
                                $scope.loadInit();
                            }, function errorCallback(res) {
                                alert(res.data.error);
                            });
                }

                $scope.saveTask = function (task) {
                    $http({
                        method: 'POST',
                        url: createTaskUrl,
                        params: {name: $scope.name, description: $scope.description}
                    }).then(
                            function successCallback(res) {
                                $scope.loadInit();
                                $scope.reset();
                            }, function errorCallback(res) {
                                alert(res.data.error);
                            });
                }

                $scope.cancelUpdate =function(){
                    $scope.state = null;
                    $scope.reset();
                }

                $scope.updateTask = function () {
                    $http({
                        method: 'POST',
                        url: updateTaskUrl,
                        params: {id: $scope.id, name: $scope.name, description: $scope.description}
                    }).then(
                            function successCallback(res) {
                                var task = $filter('filter')($scope.taskData.content, {id: $scope.id}, true);
                                if (task.length) {
                                    res.data.dateCreated = new Date(res.data.dateCreated).toLocaleString();
                                    res.data.dateUpdated = new Date(res.data.dateUpdated).toLocaleString();
                                    $scope.taskData.content[$scope.taskData.content.indexOf(task[0])] = res.data;
                                    $scope.reset();
                                }
                            }, function errorCallback(res) {
                                alert(res.data.error);
                            });
                }


            });
</script>
</html>