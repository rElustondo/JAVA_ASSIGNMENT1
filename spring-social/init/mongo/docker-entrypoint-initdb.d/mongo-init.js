print('START')

db = db.getSiblingDB('social-service');
db.createUser({
    user:'rootadmin',
    password:'password',
    roles:[{role:'readWrite', db:'social-service'}]
})

db.createCollection('user');


print('END')