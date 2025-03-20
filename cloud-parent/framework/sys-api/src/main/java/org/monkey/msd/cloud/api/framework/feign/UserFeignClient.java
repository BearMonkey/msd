package org.monkey.msd.cloud.api.framework.feign;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.monkey.msd.cloud.common.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * UserFeignClient
 *
 * @author cc
 * @since 2025/3/19 14:46
 */
@FeignClient(value = "user", path = "/user/usr-user", contextId = "userFeignClient")
public interface UserFeignClient {

    @PostMapping("/add")
    Result<Boolean> addUser(@RequestBody UsrUserDto usrUserDto);

    @PostMapping("/list")
    Result<List<UsrUserDto>> list(@RequestBody UsrUserDto usrUserDto);

    @PostMapping("/selectByUsername")
    Result<List<UsrUser>> selectByUsername(@RequestParam("username") String username);
}
