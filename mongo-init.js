db.createUser(
    {
        user: "mirante_user",
        pwd: "123456",
        roles: [
            {
                role: "readWrite",
                db: "mirante_db"
            }
        ]
    }
);