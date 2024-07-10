package at.fklab.ota_server.development

import at.fklab.ota_server.models.User

val sampleUsers = listOf<User>(
    User(
        id = 1,
        login = "test01",
        password = "test01",
        firstname = "test",
        lastname = "test",
        description = "test",
    ), User(
        id = 2,
        login = "test02",
        password = "test02",
        firstname = "test",
        lastname = "test",
        description = "test",
    ), User(
        id = 3,
        login = "testUser",
        password = "SuperSecret",
        firstname = "user",
        lastname = "Tests",
        description = "Just a user i use for tests",
    )
)