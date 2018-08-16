var json_web_token = require('jwt-simple');
var _ = require('underscore');
var nonSecurePaths = ['', '/ping', '/staff/authenticate', '/staff/register']; //Update this line to open up public routes

module.exports = function (acl, models) {
    var module = {};

    module.authenticate = async function (req, res, next) {
        var url = req.baseUrl || req.url;
        
        if (url != '/') {
            url = url.slice(3);
        }

        if (_.contains(nonSecurePaths, url)) {
            return next();
        }
        
        var token = null;
        if (req.headers && req.headers.authorization) {
            var parted = req.headers.authorization.split(' ');
            if (parted.length === 2) {
                if (parted[0].toLowerCase() == 'bearer') {
                    token = parted[1];
                }
            }
        }

        if (token) {
            try {
                var decoded = json_web_token.decode(token, 'iaarb-naj-sls');

                models.staff.findOne({
                    where: {
                        id: decoded.id
                    }       
                }).then((staff) => {
                    if (staff) {

                            res.locals.staff_id = staff.id;
                            res.locals.is_app_user = decoded.roles[0] == 'app-user';
                            var resource = req.baseUrl.split('/')[2];

                            if (resource) {
                                resource = resource.toLowerCase();
                            }
                            acl.areAnyRolesAllowed(decoded.roles[0], resource, req.method, function(err, allowed) {
                                if (err) {
                                    res.statu(500).json({ message: 'Error while checking permissions' });
                                } else {
                                    if (allowed) {
                                        next();
                                    } else {
                                        res.status(403).json({ message: 'Insufficient permissions' });
                                    }
                                }
                            });
                        } else {
                            res.setHeader('WWW-Authenticate', 'Bearer realm="SLS"');
                            res.status(401).json({ message: 'Invalid authorization token' })
                        }
                });
            } catch (err) {
                res.status(400).json({ message: 'Unreadable authorization token.' })
                console.log(err);
            }
        } else {
            res.setHeader('WWW-Authenticate', 'Bearer realm="SLS"');
            res.status(401).json({ message: 'Missing authorization token' })
        }
    }

    return module;
}