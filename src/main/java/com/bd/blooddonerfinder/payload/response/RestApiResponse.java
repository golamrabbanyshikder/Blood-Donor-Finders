package com.bd.blooddonerfinder.payload.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class RestApiResponse<T>  {
    private T data;
    private HttpStatus status;
    private String message;
    private List<T> datas;
    
    public static <T> RestApiResponse<T> success(T data, String message,List<T> datas) {
        RestApiResponse<T> response = new RestApiResponse<>();
        response.setData(data);
        response.setStatus(HttpStatus.OK);
        response.setMessage(message);
        response.setDatas(datas);
        return response;
    }

	

}
