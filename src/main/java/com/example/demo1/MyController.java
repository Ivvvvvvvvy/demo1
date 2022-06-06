package com.example.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    ExchangeRateAll exchangeRateReport;

    @RequestMapping("/test")
    public String test(){
        System.out.println("HIIIII");
        return "Hello World";
    }

    //顯示所有幣別資料表
    @GetMapping("/getAll")
    public ResponseEntity<List<ExchangeRate>> getAll(){
        try{
            List<ExchangeRate> list = exchangeRateReport.findAll();
            if(list.isEmpty() || list.size() ==0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //1. 測試呼叫查詢幣別對應表資料API，並顯示其內容
    @GetMapping("/method1/{currency}")
    public ResponseEntity<ExchangeRate> method1(@PathVariable String currency) {
        Optional<ExchangeRate> ER = exchangeRateReport.findById(currency);

        if (ER.isPresent()) {
            return new ResponseEntity<>(ER.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //2. 測試呼叫新增幣別對應表資料API
    @PostMapping("/method2")
    public ResponseEntity<ExchangeRate> method2(@RequestBody ExchangeRate exchangeRate) {
       try {
            return new ResponseEntity<>(exchangeRateReport.saveAndFlush(exchangeRate), HttpStatus.CREATED);
        } catch (Exception e) {
            //System.out.println(exchangeRate);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //3. 測試呼叫更新幣別對應表資料API，並顯示其內容。
    @PutMapping("/method3")
    public ResponseEntity<ExchangeRate> method3(@RequestBody ExchangeRate exchangeRate) {
        try {
            return new ResponseEntity<>(exchangeRateReport.save(exchangeRate), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //4. 測試呼叫刪除幣別對應表資料API。
    @DeleteMapping("/method4/{currency}")
    public ResponseEntity<HttpStatus> method4(@PathVariable String currency) {
        try {
            String[] arr = currency.split("&");
            for(int i=0; i<arr.length; i++){
                Optional<ExchangeRate> ER = exchangeRateReport.findById(arr[i]);
                if (ER.isPresent()) {
                    exchangeRateReport.delete(ER.get());
                }
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //5. 測試呼叫coindesk API，並顯示其內容
    @RequestMapping("/method5")
    public String method5(){
        //用RestTemplate呼叫API
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json",String.class);
        return result;
    }

}