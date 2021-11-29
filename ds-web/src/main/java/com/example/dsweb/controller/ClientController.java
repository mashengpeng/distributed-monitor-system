package com.example.dsweb.controller;

import com.example.dsgeneral.data.*;
import com.example.dsgeneral.enums.ResultEnum;
import com.example.dsgeneral.utils.ResultVOUtils;
import com.example.dsgeneral.vo.res.BaseResVO;
import com.example.dsweb.service.ClientService;
import com.example.dsweb.utils.PasswordUtils;
import com.example.dsweb.utils.UserLoginUtils;
import com.example.dsweb.vo.req.ClientByIpReqVO;
import com.example.dsweb.vo.req.ClientTableReqVO;
import com.example.dsweb.vo.req.UserLoginPwdReqVO;
import com.example.dsweb.vo.res.UserLoginResVO;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class ClientController {

    @Resource
    private ClientService clientService;
    @GetMapping("/client/size")
    public BaseResVO getTotalCount() {
        // 验证登录
//        UserLoginDTO userLoginDTO = UserLoginUtils.check(request);
        int count = clientService.getTotalCount();
        return ResultVOUtils.success(count);
    }

    /**
     * 首页table连表查询
     *
     * @param clientTableReqVO
     * @return
     */
    @PostMapping("/client/table")
    public BaseResVO getTableData(@Valid @RequestBody ClientTableReqVO clientTableReqVO) {
        int currIndex = clientTableReqVO.getCurrIndex();
        int pageSize = clientTableReqVO.getPageSize();
        List<VueTable> vueTables =  clientService.findAllDataByLimit(currIndex,pageSize);
        if(vueTables == null || vueTables.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "暂无服务器信息~");
        }
        return ResultVOUtils.success(vueTables);
    }
    /**
     * table by ip
     *
     * @param clientByIpReqVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/client/table/ip")
    public BaseResVO getTableDataByIp(@Valid @RequestBody ClientByIpReqVO clientByIpReqVO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }
        String ip = clientByIpReqVO.getIp();
        VueTable vueTables =  clientService.findTableDataByIp(ip);
        if(vueTables == null){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "暂无该服务器信息~");
        }
        return ResultVOUtils.success(vueTables);
    }
    /**
     * table by ip
     *
     * @param clientByIpReqVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/client/chart/ip")
    public BaseResVO getChartDataByIp(@Valid @RequestBody ClientByIpReqVO clientByIpReqVO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }
        String ip = clientByIpReqVO.getIp();
        Cpu cpu =  clientService.findCpuByIp(ip);
        Meo meo = clientService.findMeoByIp(ip);
        NetWork net = clientService.findNetByIp(ip);
        List<DiskMes> diskMes = clientService.findDiskMesByIp(ip);
        List<FileMes> fileMes = clientService.findFileMesByIp(ip);
        SumData sumData = new SumData();
        sumData.setFileMes(fileMes);
        sumData.setNetWork(net);
        sumData.setMeo(meo);
        sumData.setCpu(cpu);
        sumData.setDiskMes(diskMes);
        return ResultVOUtils.success(sumData);
    }
}
