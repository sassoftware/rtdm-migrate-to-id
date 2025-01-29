package com.sas.changeme;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/skeleton", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SkeletonController
{
   // TODO: Add Content-Types
   @RequestMapping(method = RequestMethod.GET)
   public String get() {

      String retVal = "{ \"message\":\"Hello Bones!\"}";

      return retVal;
   }
}
