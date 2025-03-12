package com.interesting.modules.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.customer.dto.AddXqCustomerDTO;
import com.interesting.modules.customer.dto.QueryXqCustomerDTO;
import com.interesting.modules.customer.dto.UpdateXqCustomerDTO;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.vo.CustomerIdNameAndAddressVO;
import com.interesting.modules.customer.vo.XqCustomerVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description: 客户表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface IXqCustomerService extends IService<XqCustomer> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqCustomerVO> pageList(Page<XqCustomerVO> page, QueryXqCustomerDTO dto);

    XqCustomerVO getDetailById(String id);

    boolean saveCustomer(AddXqCustomerDTO dto);

    boolean updateCustomerById(UpdateXqCustomerDTO dto);

    /**
     * 导出客户模板
     */
    void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 导入客户
     * @param file
     * @return
     * @throws Exception
     */
    Result<?> importCustomer(MultipartFile file) throws Exception;

    List<CustomerIdNameAndAddressVO> listCustomer();

    void customerExport(Page<XqCustomerVO> page, QueryXqCustomerDTO dto, HttpServletResponse response);
}
