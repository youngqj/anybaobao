package com.interesting.modules.supplier.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.supplier.dto.AddXqSupplierDTO;
import com.interesting.modules.supplier.dto.UpdateXqSupplierDTO;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.supplier.dto.QueryXqSupplierDTO;
import com.interesting.modules.supplier.vo.XqSupplierVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 供应商表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface IXqSupplierService extends IService<XqSupplier> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqSupplierVO> pageList(Page<XqSupplierVO> page, QueryXqSupplierDTO dto);

    XqSupplierVO getCustomerById(String id);

    boolean saveSupplier(AddXqSupplierDTO dto);

    boolean updateSupplierById(UpdateXqSupplierDTO dto);

    /**
     * 导出供应商模板
     */
    void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 导入供应商
     * @param file
     * @return
     * @throws Exception
     */
    Result<?> importSupplier(MultipartFile file) throws Exception;

    void supplierExport(Page<XqSupplierVO> page, QueryXqSupplierDTO dto, HttpServletResponse response);
}
