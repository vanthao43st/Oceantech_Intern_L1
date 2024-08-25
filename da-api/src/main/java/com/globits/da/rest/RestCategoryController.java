package com.globits.da.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import com.globits.da.dto.MyFirstApiDto;
import com.globits.da.service.MyFirstApiService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.globits.da.AFFakeConstants;
import com.globits.da.dto.CategoryDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/category")
public class RestCategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    MyFirstApiService myFirstApiService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoryDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<CategoryDto> results = categoryService.getPage(pageSize, pageIndex);
        return new ResponseEntity<Page<CategoryDto>>(results, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto dto) {
        CategoryDto result = categoryService.saveOrUpdate(null, dto);
        return new ResponseEntity<CategoryDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto dto, @PathVariable UUID id) {
        CategoryDto result = categoryService.saveOrUpdate(id, dto);
        return new ResponseEntity<CategoryDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CategoryDto> getList(@PathVariable UUID id) {
        CategoryDto result = categoryService.getCertificate(id);
        return new ResponseEntity<CategoryDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        Boolean result = categoryService.deleteKho(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllCategory", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> result = categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
    public ResponseEntity<Page<CategoryDto>> searchByPage(@RequestBody SearchDto searchDto) {
        Page<CategoryDto> page = this.categoryService.searchByPage(searchDto);
        return new ResponseEntity<Page<CategoryDto>>(page, HttpStatus.OK);
    }

    //	@Secured({  AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN })
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required = false) UUID id,
                                             @RequestParam("code") String code) {
        Boolean result = categoryService.checkCode(id, code);
        return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


//	@GetMapping(value = "/getById/{id}")
//	public EmployeeDTO getById(UUID id)
//	{
//		EmployeeDTO employeeDTO = employeeService.getById(id);
//		return employeeDTO;
//		
//	}

    @GetMapping("/myfirstapi")
    public ResponseEntity<String> getMyFirstApi() {
        return new ResponseEntity<String>("My First Api", HttpStatus.OK);
    }

    @GetMapping("/myfirstapiservice")
    public ResponseEntity<String> getMyFirstApiService() {
        String result = myFirstApiService.getMyFirstApiService();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/myfirstapi")
    public ResponseEntity<MyFirstApiDto> postMyFirstApi(@RequestBody MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @PostMapping("/myfirstapi/formdata")
    public ResponseEntity<MyFirstApiDto> postMyFirstApiformdata(@RequestParam String code, @RequestParam String name,
                                                                @RequestParam Integer age) {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @PostMapping("/myfirstapi/{code}/{name}/{age}")
    public ResponseEntity<MyFirstApiDto> postMyFirstApiPathvariable(@PathVariable("code") String code, @PathVariable("name") String name,
                                                                    @PathVariable("age") Integer age) {
        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
        myFirstApiDto.setCode(code);
        myFirstApiDto.setName(name);
        myFirstApiDto.setAge(age);

        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

//    @PostMapping("/myfirstapi/no-annotation")
//    public ResponseEntity<MyFirstApiDto> postMyFirstApiformdata(HttpServletRequest request) throws IOException {
//        MyFirstApiDto myFirstApiDto = new MyFirstApiDto();
//
//        String code = request.getParameter("code");
//        String name = request.getParameter("name");
//        String age = request.getParameter("age");
//
//        if (code == null && name == null && age == null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            myFirstApiDto = objectMapper.readValue(request.getInputStream(), MyFirstApiDto.class);
//        } else {
//            myFirstApiDto.setCode(code);
//            myFirstApiDto.setName(name);
//            myFirstApiDto.setAge(Integer.valueOf(age));
//        }
//
//        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
//    }

    @PostMapping("/myfirstapi/no-annotation")
    public ResponseEntity<MyFirstApiDto> postMyFirstApiformdata(MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

    @PostMapping("/myfirstapi/uploadfile") public ResponseEntity<String> postMyFirstApiWithFile(@RequestParam("files") MultipartFile[] submissions) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        for (MultipartFile file : submissions){
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

        return new ResponseEntity<>("File uploaded successfully.", HttpStatus.OK);
    }

    @PostMapping("/myfirstapi/no-requestbody")
    public ResponseEntity<MyFirstApiDto> postMyFirstApiWithNoRequestbody(MyFirstApiDto myFirstApiDto) {
        return new ResponseEntity<>(myFirstApiDto, HttpStatus.OK);
    }

}
