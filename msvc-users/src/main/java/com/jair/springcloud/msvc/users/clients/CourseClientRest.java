package com.jair.springcloud.msvc.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "host.docker.internal:8081")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{id}")
    void deleteCourseUserByUserId(@PathVariable Long id);
}
