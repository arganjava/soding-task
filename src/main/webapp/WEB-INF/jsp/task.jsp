<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Task</title>
    <script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
</head>
<body>
${message}
<div class="table-responsive">
    <table class="table col-sm-10" id="tblTask">
        <thead>
        <th>Name</th>
        <th>Description</th>
        <th>Create Date</th>
        <th>Update Date</th>
        <th>Action</th>
        </thead>
        <thead>
        <th><input></th>
        <th><input></th>
        <th></th>
        <th></th>
        <th>
            <button>Save</button>
        </th>
        </thead>
        <tbody>
        <tr></tr>
        </tbody>
    </table>
</div>


</body>
<script type="text/javascript">
    var tblRow = $('#tblTask > tbody');
    var param = {
        pageCondition: "isInit",
        pageNumber: "0"
    }
    var getAllTask = '<c:url value="/getAll"/>';
    var createTaskUrl = '<c:url value="/create"/>';
    var updateTaskUrl = '<c:url value="/update"/>';
    var deleteTaskUrl = '<c:url value="/delete"/>';

    $(document).ready(function () {
        loadTask(param);


        function createTask(param) {
            $.post(createTaskUrl,
                    param, function (data, res) {
                        if (res == 'success') {
                            loadTask(param);
                        } else {
                            alert(res.error);
                        }
                    });
        }

        function updateTask(param) {
            $.post(updateTaskUrl,
                    param, function (data, res) {
                        if (res == 'success') {
                            loadTask(param);
                        } else {
                            alert(res.error);
                        }
                    });
        }

        function deleteTask(param) {
            $.post(deleteTaskUrl,
                    param, function (data, res) {
                        if (res == 'success') {
                            loadTask(param);
                        } else {
                            alert(res.error);
                        }
                    });
        }

        function loadTask(param) {
            $.get(getAllTask,
                    param, function (data, res) {
                        if (res == 'success') {
                            for (var i = 0; i < data.content.length; i++) {
                                var task = data.content[i];
                                var dateCreated = task.dateCreated == null ? '' : new Date(task.dateCreated).toLocaleString()

                                var dateUpdated = task.dateUpdated == null ? '' : new Date(task.dateUpdated).toLocaleString()
                                tblRow.append('<tr><td>' + task.name + '</td> ' +
                                        '<td>' + task.description + '</td>' +
                                        '<td>' + dateCreated + '</td>' +
                                        '<td>' + dateUpdated + '</td>' +
                                        '<td><button class="btn-primary">Update</button>&nbsp;' +
                                        '<button class="btn-danger">Delete</button></td>' +
                                        '</tr>');
                            }
                        }
                    });
        }

    });
</script>
</html>