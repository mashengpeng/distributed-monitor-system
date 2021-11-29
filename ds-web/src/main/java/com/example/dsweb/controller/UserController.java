package com.example.dsweb.controller;

import com.example.dsgeneral.data.User;
import com.example.dsgeneral.enums.ResultEnum;
import com.example.dsgeneral.utils.ResultVOUtils;
import com.example.dsgeneral.vo.res.BaseResVO;
import com.example.dsweb.service.UserService;
import com.example.dsweb.utils.PasswordUtils;
import com.example.dsweb.utils.UserLoginUtils;
import com.example.dsweb.vo.req.UserLoginPwdReqVO;
import com.example.dsweb.vo.req.UserNameValidate;
import com.example.dsweb.vo.res.UserLoginResVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户密码登录
     *
     * @param userLoginPwdReqVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public BaseResVO login(@Valid @RequestBody UserLoginPwdReqVO userLoginPwdReqVO,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String username = userLoginPwdReqVO.getUsername();
        System.out.println("username:" + username);
        User user = userService.findUserByName(username);
        String md5Pwd = PasswordUtils.md52md5(userLoginPwdReqVO.getPassword());
        System.out.println("Pwd:" + userLoginPwdReqVO.getPassword());
        if (user == null || !md5Pwd.equals(user.getPwd())) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "密码或用户名错误~");
        }
        String token = UserLoginUtils.createSid(user.getId());

        UserLoginResVO userLoginResVO = new UserLoginResVO();
        userLoginResVO.setUid(user.getId());
        userLoginResVO.setSid(token);
        userLoginResVO.setUsername(user.getUsername());
        return ResultVOUtils.success(userLoginResVO);
    }
    /**
     * 验证用户名是否已存在
     *
     * @param userNameValidate
     * @param bindingResult
     * @return
     */
    @PostMapping("/validate")
    public BaseResVO validate(@Valid @RequestBody UserNameValidate userNameValidate,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String username = userNameValidate.getUsername();
        User user = userService.findUserByName(username);
        if (user != null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "用户名已存在~");
        }
        return ResultVOUtils.success();
    }
    /**
     * 注册
     *
     * @param userLoginPwdReqVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/regist")
    public BaseResVO regist(@Valid @RequestBody UserLoginPwdReqVO userLoginPwdReqVO,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String username = userLoginPwdReqVO.getUsername();
        User user = userService.findUserByName(username);
        if (user != null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "用户名已存在~");
        }
        user = new User();
        user.setUsername(username);
        String md5Pwd = PasswordUtils.md52md5(userLoginPwdReqVO.getPassword());
        user.setPwd(md5Pwd);
        boolean isRegist = userService.insertUser(user);
        if(isRegist){
            String token = UserLoginUtils.createSid(user.getId());
            UserLoginResVO userLoginResVO = new UserLoginResVO();
            userLoginResVO.setSid(token);
            userLoginResVO.setUsername(user.getUsername());
            return ResultVOUtils.success(userLoginResVO);
        }else{
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "注册失败");
        }

    }

//    @GetMapping("/getAllClients")
//    public String getAllClients(){
//        List<Client> list = clientMapper.getAllClients();
//        for(Client client:list){
//            System.out.println(client.test());
//        }
//        return clientMapper.getAllClients().toString();
//    }
//
//    @GetMapping("/insert")
//    public String insert(){
//        clientMapper.insert(new Client());
//        return clientMapper.getAllClients().toString();
//    }
//
//    @GetMapping("/getClientsByHostName")
//    public String getClientsByHostName(){
//        Client client = clientMapper.getClientsByHostName("");
//        return client.toString();
//    }
//
//    @GetMapping("/deleteAll")
//    public String deleteAll(){
//        clientMapper.deleteAll();
//        return clientMapper.getAllClients().toString();
//    }



}
