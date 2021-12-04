package com.example.dsweb.controller;

import com.example.dsgeneral.data.OfflineMes;
import com.example.dsgeneral.data.VueTable;
import com.example.dsgeneral.enums.ResultEnum;
import com.example.dsgeneral.utils.ResultVOUtils;
import com.example.dsgeneral.vo.res.BaseResVO;
import com.example.dsweb.dao.OfflineDao;
import com.example.dsweb.vo.req.ClientByIpReqVO;
import com.example.dsweb.vo.req.ClientTableReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class OfflineController {

    @Autowired
    OfflineDao offlineDao;

    /**
     * offlinetable连表查询
     *
     * @param clientTableReqVO
     * @return
     */
    @PostMapping("/offline/table")
    public BaseResVO getOfflineData(@Valid @RequestBody ClientTableReqVO clientTableReqVO) {
        log.info("查询异常服务器");
        int currIndex = clientTableReqVO.getCurrIndex();
        int pageSize = clientTableReqVO.getPageSize();
        List<OfflineMes> offlineMes =  offlineDao.findAllOfflineByLimit(currIndex,pageSize);
        if(offlineMes == null || offlineMes.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "暂无异常服务器信息~");
        }
        return ResultVOUtils.success(offlineMes);
    }

    @PostMapping("/offline/table/ip")
    public BaseResVO getTableDataByIp(@Valid @RequestBody ClientByIpReqVO clientByIpReqVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }
        String ip = clientByIpReqVO.getIp();
        OfflineMes offlineMes = offlineDao.findOfflineByIp(ip);

        if(offlineMes == null){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "这台服务器暂无异常信息~");
        }
        return ResultVOUtils.success(offlineMes);
    }


}
