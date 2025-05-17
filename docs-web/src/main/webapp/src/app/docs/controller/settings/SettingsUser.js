'use strict';

/**
 * Settings user page controller.
 */
angular.module('docs').controller('SettingsUser', function($scope, $state, Restangular, $http) {
  /**
   * Load users from server.
   */
  $scope.loadUsers = function() {
    Restangular.one('user/list').get({
      sort_column: 1,
      asc: true
    }).then(function(data) {
      $scope.users = data.users;
    });
  };
  
  $scope.loadUsers();
  
  /**
   * Edit a user.
   */
  $scope.editUser = function(user) {
    $state.go('settings.user.edit', { username: user.username });
  };

  // 加载注册申请列表
  $scope.loadUserRequests = function () {
    $http.get('/docs-web/api/userRequests', { responseType: 'text' }).then(function (response) {
      console.log(response);
      var lines = response.data.trim().split('\n');
      $scope.requests = lines.map(function(line) {
        // 解析一行文本为对象
        // 假设格式是：ID: xxx, Email: xxx, Status: xxx, Created: xxx
        var obj = {};
        line.split(',').forEach(function(part) {
          var kv = part.split(':');
          if (kv.length >= 2) {
            var key = kv[0].trim().toLowerCase(); // id, email, status, created
            var value = kv.slice(1).join(':').trim();
            obj[key] = value;
          }
        });
        // 模拟name和message字段为空字符串，后端没有返回的话
        obj.name = obj.name || '';
        obj.message = obj.message || '';
        obj.id = obj.id || obj['id']; // 保证有id字段
        console.log(obj);
        return obj;
      });
    }, function (err) {
      console.error('加载注册请求失败', err);
    });
  };





  // 审批通过
  $scope.approve = function (id) {
    Restangular.one('userRequests', id).customPOST(null, 'approve').then(function () {
      $dialog.messageBox('Success', 'Request approved.', [{ result: 'ok', label: 'OK' }]);
      $scope.loadUserRequests();
      console.log('审批成功');
    }, function(err) {
      console.error('审批请求失败', err);
    });
  };

  // 拒绝申请
  $scope.reject = function (id) {
    Restangular.one('userRequests', id).customPOST(null, 'reject').then(function () {
      $dialog.messageBox('Success', 'Request rejected.', [{ result: 'ok', label: 'OK' }]);
      $scope.loadUserRequests();
    }, function(err) {
      console.error('拒绝请求失败', err);
    });
  };

  // 初始化加载
  $scope.loadUserRequests();
});