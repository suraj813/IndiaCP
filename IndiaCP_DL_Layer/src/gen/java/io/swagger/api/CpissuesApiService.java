package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.CPIssue;
import io.swagger.model.Error;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-12-21T10:49:10.288Z")
public abstract class CpissuesApiService {
    public abstract Response fetchAllCP(String entity,SecurityContext securityContext) throws NotFoundException;
    public abstract Response fetchAllCPForCPProgram(String issuer,String cpProgramId,SecurityContext securityContext) throws NotFoundException;
}
