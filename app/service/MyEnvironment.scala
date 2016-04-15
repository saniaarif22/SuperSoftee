package service

import securesocial.controllers.ViewTemplates
import securesocial.core.RuntimeEnvironment
import securesocial.core.services.UserService

class MyEnvironment extends RuntimeEnvironment.Default {
    type U = User
    override val userService: UserService[U] = new InMemoryUserService()
    //override lazy val viewTemplates: ViewTemplates = MyViewTemplates
}