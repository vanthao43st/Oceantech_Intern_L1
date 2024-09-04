package com.globits.da.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globits.da.dto.MyFirstApiDto;
import com.globits.da.service.MyFirstApiService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/myfirstapi")
public class RestMyFirstApiController {

    @Autowired
    MyFirstApiService myFirstApiService;

    @RequestMapping(value = "/getmyfirstapi", method = RequestMethod.GET)
    public ResponseEntity<String> getMyFirstApi() {
        return new ResponseEntity<String>("MyFirstApi", HttpStatus.OK);
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ResponseEntity<String> getMyFirstApiService() {
        String result = myFirstApiService.getMyFirstApiService();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/postmyfirstapi", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApi(@RequestBody MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/formdata/requestbody", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiFormData(@RequestBody MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/primitive/json", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiPrimitiveJsonData(@RequestBody MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/primitive/formdata", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiPrimitiveFormData(@RequestParam String code,
                                                                         @RequestParam String name,
                                                                         @RequestParam int age) {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/formdata/requestparam", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiFormData(@RequestParam String code, @RequestParam String name,
                                                                @RequestParam int age) {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{code}/{name}/{age}", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiPathVariable(@PathVariable("code") String code, @PathVariable("name") String name,
                                                                    @PathVariable("age") Integer age) {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/no-annotation/jsondata", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiNoAnnotationJsonData(MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/no-annotation/formdata", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiNoAnnotationFormData(String code, String name,
                                                                            int age) {

        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/no-annotation/fix-jsondata", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiNoAnnotationFixJsonData(HttpServletRequest request) throws IOException {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        if (code == null && name == null && age == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            myFirstApiDto = objectMapper.readValue(request.getInputStream(), MyFirstApiDto.class);
        } else {
            myFirstApiDto.setCode(code);
            myFirstApiDto.setName(name);
            myFirstApiDto.setAge(Integer.valueOf(age));
        }

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload-file-txt")
    public ResponseEntity<String> uploadFileTxt(@RequestParam("filetxt") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        System.out.println("Processing file: " + filename);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        return new ResponseEntity<>("File uploaded successfully.", HttpStatus.OK);
    }


    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    public ResponseEntity<String> postMyFirstApiWithFile(@RequestParam("files") MultipartFile[] submissions) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        for (MultipartFile file : submissions) {
            String filename = file.getOriginalFilename();
            System.out.println("Processing file: " + filename);
            if (filename.endsWith(".txt")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
                InputStream inputStream = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        switch (cell.getCellTypeEnum()) {
                            case STRING:
                                stringBuilder.append(cell.getStringCellValue()).append("\n");
                                break;
                            case NUMERIC:
                                stringBuilder.append(cell.getNumericCellValue()).append("\n");
                                break;
                        }
                    }
                }
            }
        }

        System.out.println(stringBuilder.toString());

        return new ResponseEntity<>("Files uploaded successfully.", HttpStatus.OK);
    }

    @RequestMapping(value = "/no-requestbody", method = RequestMethod.POST)
    public ResponseEntity<MyFirstApiDto> postMyFirstApiWithNoRequestbody(MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

}
