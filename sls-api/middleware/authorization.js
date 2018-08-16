module.exports = {
    initialize: function(acl) {
        var admin_routes = []
        var app_user_routes = ['staff'];
        acl.allow('admin', admin_routes.concat(app_user_routes), '*');
        acl.allow('app-user', app_user_routes, '*');
    }
}