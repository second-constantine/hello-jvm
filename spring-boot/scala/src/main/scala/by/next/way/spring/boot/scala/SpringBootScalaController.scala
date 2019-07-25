package by.next.way.spring.boot.scala

import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class SpringBootScalaController {

  @GetMapping(Array("/"))
  def hello() = {
    "Hello world!"
  }

}
