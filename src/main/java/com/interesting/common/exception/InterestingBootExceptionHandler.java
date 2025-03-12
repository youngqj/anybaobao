package com.interesting.common.exception;

import com.interesting.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

/**
 * 异常处理器
 *
 * @author: interesting-boot
 * @Date 2019
 */
@RestControllerAdvice
@Slf4j
public class InterestingBootExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(InterestingBootException.class)
	public Result<?> handleRRException(InterestingBootException e){
		log.debug(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return Result.error("记录已经存在了哦");
	}

//	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//	public Result<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
//		log.error(e.getMessage(), e);
//		return Result.error("记录已经存在了哦");
//	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result<?> errorResult(MethodArgumentNotValidException e){
		BindingResult bindingResult = e.getBindingResult();
		return Result.noauth(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
	}

	@ExceptionHandler({ AuthorizationException.class})
	public Result<?> handleAuthorizationException(AuthorizationException e){
		log.error(e.getMessage(), e);
		return Result.noauth("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}

	/**
	 * @Author 政辉
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		sb.append("支持以下");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			for(String str:methods){
				sb.append(str);
				sb.append("、");
			}
		}
		log.error(sb.toString(), e);
		//return Result.error("没有权限，请联系管理员授权");
		return Result.error(405,sb.toString());
	}

	 /**
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
	  */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return Result.error("文件大小超出100MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    	log.error(e.getMessage(), e);
        return Result.error("执行数据库异常,违反了完整性例如：违反惟一约束、违反非空限制、字段内容超出长度等");
    }

}

