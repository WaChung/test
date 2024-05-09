package com.example.demo;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TestService2;
import com.example.demo.service.TestServiceImpl;
import com.example.trace.log.MethodTrace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;


@RestController
public class HelloWorldController implements Serializable{
    private static final long serialVersionUID = 1L;
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired(required = false)
	TestService2 testService2;
	private static final String QUERY2 = "select * from corporate_customer WHERE  ocmd_id  = 'AC000002079230000024' ";
	private static final String QUERY = "select count(1) as count from corporate_customer";

    private static final String SUCCESS = "Success";
    private static final String FAIL = "fail";
	//@Value("${spring.datasource.url}")
	//private String DB_URL;
	
	private String SOURCE_DB_URL = "jdbc:sqlserver://10.97.111.187:1433;databaseName=db_cdcstream";

	private String TARGET_DB_URL = "jdbc:sqlserver://10.202.161.12:1433;databaseName=db_datasync";
	@Autowired
	private TestServiceImpl testService; 
	
	@RequestMapping("/test2")
	public String test2() throws InterruptedException {
		   return "test";
		   
	}	
    @Cacheable(value = "shortterm-cache", key="#root.targetClass+'.'+#root.methodName")	
    @GetMapping("/ping")
    public CustomResponseEntity<PingAPIResponse> ping(@RequestParam(defaultValue = "quick") String mode) throws InterruptedException
    {
    	if (testService2 != null) {
    		testService2.test();
    	}
    	System.out.println(new Date());
    	PingAPIResponse response = new PingAPIResponse();
    	Map <String, String> connectionPoolDataMap = new HashMap <String, String>();
    	Map <String, String> connectionPoolMsgMap = new HashMap <String, String>();
    	connectionPoolDataMap.put("service", "Paas DB connection Pool");
    	// Health check for the DB connection for connection pool
    		connectionPoolDataMap.put("status", SUCCESS);
    		connectionPoolDataMap.put("description", "");
    	response.getData().add(connectionPoolDataMap);
    	Map <String, String> jdbcDataMap = new HashMap <String, String>();
    	Map <String, String> jdbcMsgMap = new HashMap <String, String>();
    	jdbcDataMap.put("service", "Paas DB native JDBC");
    	
    	//Health check for the DB connection by native JDBC 
		//if using service principal, the DB_URL should be including the client_id and secret
			jdbcDataMap.put("status", SUCCESS);
			jdbcDataMap.put("description", "");

     	response.getData().add(jdbcDataMap);
    	response.getMeta().setCompletedTime(Timestamp.from(Instant.now()));
    	
    	
        return new CustomResponseEntity<PingAPIResponse>(response, HttpStatus.OK);
    }
    // @Cacheable(value = "shortterm-cache", key="#root.targetClass+'.'+#root.methodName")	
    @GetMapping("/ping2")
    @MethodTrace
   // @Cacheable(value = "shortterm-cache")	
    public CustomResponseEntity<PingAPIResponse> ping2(@RequestParam(defaultValue = "quick") String mode) throws InterruptedException
    {
    	System.out.println("Hello world");
    	
    	

    	testService.test();

    	testService.test2();
    	PingAPIResponse response = new PingAPIResponse();
    	this.test2();
    	Map <String, String> connectionPoolDataMap = new HashMap <String, String>();
    	Map <String, String> connectionPoolMsgMap = new HashMap <String, String>();
    	connectionPoolDataMap.put("service", "Paas DB connection Pool");
    	// Health check for the DB connection for connection pool
    		connectionPoolDataMap.put("status", SUCCESS);
    		connectionPoolDataMap.put("description", "");
    	response.getData().add(connectionPoolDataMap);
    	Map <String, String> jdbcDataMap = new HashMap <String, String>();
    	Map <String, String> jdbcMsgMap = new HashMap <String, String>();
    	jdbcDataMap.put("service", "Paas DB native JDBC");
    	
    	//Health check for the DB connection by native JDBC 
		//if using service principal, the DB_URL should be including the client_id and secret
			jdbcDataMap.put("status", SUCCESS);
			jdbcDataMap.put("description", "");

     	response.getData().add(jdbcDataMap);
    	response.getMeta().setCompletedTime(Timestamp.from(Instant.now()));
    	
    	
        return new CustomResponseEntity<PingAPIResponse>(response, HttpStatus.OK);
    }
    
    @Cacheable(value = "shortterm-cache", key="#root.targetClass+'.'+#root.methodName")	
    @GetMapping("/ping3")
    public CustomResponseEntity<PingAPIResponse> ping3(@RequestParam(defaultValue = "quick") String mode) throws InterruptedException
    {
    	System.out.println(new Date());
    	PingAPIResponse response = new PingAPIResponse();
    	
    	Map <String, String> connectionPoolDataMap = new HashMap <String, String>();
    	Map <String, String> connectionPoolMsgMap = new HashMap <String, String>();
    	connectionPoolDataMap.put("service", "Paas DB connection Pool");
    	// Health check for the DB connection for connection pool
    		connectionPoolDataMap.put("status", SUCCESS);
    		connectionPoolDataMap.put("description", "");
    	response.getData().add(connectionPoolDataMap);
    	Map <String, String> jdbcDataMap = new HashMap <String, String>();
    	Map <String, String> jdbcMsgMap = new HashMap <String, String>();
    	jdbcDataMap.put("service", "Paas DB native JDBC");
    	
    	//Health check for the DB connection by native JDBC 
		//if using service principal, the DB_URL should be including the client_id and secret
			jdbcDataMap.put("status", SUCCESS);
			jdbcDataMap.put("description", "");

     	response.getData().add(jdbcDataMap);
    	response.getMeta().setCompletedTime(Timestamp.from(Instant.now()));
    	
    	
        return new CustomResponseEntity<PingAPIResponse>(response, HttpStatus.OK);
    }
    
    @GetMapping("/failure")
    public ResponseEntity<PingAPIResponse> failure(@RequestParam(defaultValue = "quick") String mode) throws InterruptedException
    {
    	
    	PingAPIResponse response = new PingAPIResponse();
    	
    	Map <String, String> connectionPoolDataMap = new HashMap <String, String>();
    	Map <String, String> connectionPoolMsgMap = new HashMap <String, String>();
    	connectionPoolDataMap.put("service", "Paas DB connection Pool");
    	// Health check for the DB connection for connection pool
    		connectionPoolDataMap.put("status", SUCCESS);
    		connectionPoolDataMap.put("description", "");
    	response.getData().add(connectionPoolDataMap);
    	Map <String, String> jdbcDataMap = new HashMap <String, String>();
    	Map <String, String> jdbcMsgMap = new HashMap <String, String>();
    	jdbcDataMap.put("service", "Paas DB native JDBC");
    	
    	//Health check for the DB connection by native JDBC 
		//if using service principal, the DB_URL should be including the client_id and secret
			jdbcDataMap.put("status", SUCCESS);
			jdbcDataMap.put("description", "");

     	response.getData().add(jdbcDataMap);
    	response.getMeta().setCompletedTime(Timestamp.from(Instant.now()));
    	
    	
        return new ResponseEntity<PingAPIResponse>(response, HttpStatus.OK);
    }
    
	
    @PostMapping("/dbDataValidate")
    public ResponseEntity<Boolean> dbDataValidate(@RequestBody DBValidateRequestBody body) throws InterruptedException
    {
    	String dbName = body.getDbName();
    	String tableName = body.getTableName();
    	String fields = body.getFields();
    	String condition = body.getCondition();
    	//String expectedResultInJson = "[{\"employment_data_update_datetime\":\"2022-08-11 01:55:02.997\",\"smoker_indicator_update_datetime\":\"2022-08-11 01:55:02.997\",\"marital_status_code\":\"M\",\"disabled_indicator_update_datetime\":\"2022-08-11 01:55:02.997\",\"gender\":\"M\",\"aml_status_code\":\"Y\",\"dob_update_datetime\":\"2022-08-11 01:55:02.997\",\"title_update_datetime\":\"2022-08-11 01:55:02.997\",\"dob_source_system_code\":\"CM\",\"occupation_class_source_value\":\"\",\"black_list_indicator\":\"Y\",\"us_citizen_update_datetime\":\"2022-08-11 01:55:02.997\",\"name_source_system_code\":\"CM\",\"occupation_code\":\"10001\",\"occupation_data_update_datetime\":\"2022-08-11 01:55:02.997\",\"ocmd_id\":\"C000002035\",\"first_name\":\"TestDQ\",\"alias_name_update_datetime\":\"2022-08-11 01:55:02.997\",\"customer_type_code\":\"individual\",\"critical_illness_indicator_update_datetime\":\"2022-08-11 01:55:02.997\",\"merged_to_ocmd_id\":\"Y\",\"last_update_datetime\":\"2022-08-10 23:00:00.0\",\"last_name\":\"Bob\",\"middle_name\":\"\",\"salary_wealth_data_update_datetime\":\"2022-08-11 01:55:02.997\",\"name_update_datetime\":\"2022-08-11 01:55:02.997\",\"full_name\":\"TestDQ Bob\",\"dob\":\"1999-11-13 00:00:00.0\",\"customer_market_segment_update_datetime\":\"2022-08-11 01:55:02.997\",\"current_status_code\":\"AC\",\"gender_update_datetime\":\"2022-08-11 01:55:02.997\",\"record_effective_datetime\":\"2022-08-11 01:55:02.997\",\"current_status_effective_datetime\":\"2022-08-11 01:55:02.997\",\"marital_status_update_datetime\":\"2022-08-11 01:55:02.997\",\"aids_limit_checking_indicator_update_datetime\":\"2022-08-11 01:55:02.996\",\"gender_source_system_code\":\"CM\"}]";
    	String expectedResultInJson = body.getExpectedResultInJson();
    	JSONArray dbResultJson = null;
    	Boolean result = false;
    	//1. construct the SQL query by request body.
    	String qeury = "select " + fields + " from "+ tableName + " where " + condition + ";";
    	//2. execute query and return json object
    	//remark:  do not hard code user name and password
		try (Connection conn = DriverManager.getConnection(SOURCE_DB_URL, "hdeflow", "8ik,.lo(");
				//remark: use preparestatement instead of statement
				Statement stmt = conn.createStatement();
				// execute query for heath check
				ResultSet rs = stmt.executeQuery(qeury)) {
			    // convert result to json array
				dbResultJson = resultSet2JsonArr(rs);
			    System.out.println(dbResultJson);
			// catch SQLException if any
		} catch (SQLException e) {
			e.printStackTrace();
		}	
    	//3. compare db result with expected result
		try {
			JSONCompareResult jsonCompareReuslt = JSONCompare.compareJSON(dbResultJson.toString(), expectedResultInJson,  JSONCompareMode.NON_EXTENSIBLE);
			result = jsonCompareReuslt.passed();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//4. return validation result.
    	
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
    
	
	
	@RequestMapping("/test")
	public void test() throws InterruptedException {
		   System.out.println("Total Number of threads " + ManagementFactory.getThreadMXBean().getThreadCount());

         ObjectMapper mapper = new ObjectMapper();
         mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
         try {
//        	 JsonNode actualObj = mapper.readTree("[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]");
//        	 JsonNode actualObj2 = mapper.readTree("[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]");
        	 JsonNode actualObj = mapper.readTree("{\"abc\" : \" abc\", \"bcd\": \"bcd\"}");
        	 JsonNode actualObj2 = mapper.readTree("{\"bcd\" : \"bcd\", \"abc\" : \"abc\", \"abcD\" : \"abcD\"}");
        	 Predicate[] lengthIsEven = {null};
             DocumentContext jsonContext = JsonPath.parse("{\"abc\" : \" abc\", \"bcd\": \"bcd\"}");
             jsonContext.delete("$.bcd", lengthIsEven);
             
             
             
             String update = jsonContext.jsonString();
             System.out.println(update);

String[] excludeFieldsArr = update.split(",");				/*
				 * String input1 = "{\"state\":1,\"cmd\":1}"; String input2 =
				 * "{\"cmd\":1,\"state\":1, \" test \":\"andbcasd\"}"; //String input1 =
				 * "[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]"
				 * ; // String input1 =
				 * "[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]"
				 * ; // String input2 =
				 * "[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]"
				 * ; ObjectMapper om = new ObjectMapper();
				 * 
				 * 
				 * try { JSONCompareResult result = JSONCompare.
				 * compareJSON("[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]\n"
				 * + "",
				 * "[{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008780aac31001185ed05\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Owning a cat can reduce the risk of stroke and heart attack by a third.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-29T20:20:03.844Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e009390aac31001185ed10\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Most cats are lactose intolerant, and milk can cause painful stomach cramps and diarrhea. It's best to forego the milk and just give your cat the standard: clean, cool drinking water.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-04T21:20:02.979Z\",\"deleted\":false,\"used\":false},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"588e746706ac2b00110e59ff\",\"user\":\"588e6e8806ac2b00110e59c3\",\"text\":\"Domestic cats spend about 70 percent of the day sleeping and 15 percent of the day grooming.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-26T20:20:02.359Z\",\"type\":\"cat\",\"createdAt\":\"2018-01-14T21:20:02.750Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e008ad0aac31001185ed0c\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-24T20:20:01.867Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-15T20:20:03.281Z\",\"deleted\":false,\"used\":true},{\"status\":{\"verified\":true,\"sentCount\":1},\"_id\":\"58e007cc0aac31001185ecf5\",\"user\":\"58e007480aac31001185ecef\",\"text\":\"Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs.\",\"__v\":0,\"source\":\"user\",\"updatedAt\":\"2020-08-23T20:20:01.611Z\",\"type\":\"cat\",\"createdAt\":\"2018-03-01T21:20:02.713Z\",\"deleted\":false,\"used\":false}]\n"
				 * + "", JSONCompareMode.LENIENT); JSONCompareResult result1 =
				 * JSONCompare.compareJSON(input1, input2, JSONCompareMode.NON_EXTENSIBLE);
				 * System.out.println(result.passed()); } catch (Exception e) {
				 * e.printStackTrace(); }
				 */
        	 
        	 //actualObj.e
        	 //System.out.println(actualObj.equals(actualObj2));
         } catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	@RequestMapping("/")
	public String index() throws InterruptedException {

		Thread currentThread = Thread.currentThread();
		// List<String> strings = new ArrayList<String>();
		// strings.add("A"); strings.add("B"); strings.add("C");
//		  strings.parallelStream().forEach( a -> {
//		 System.out.println(Thread.currentThread().getName() ); System.out.println(a);
//		  try { Thread.sleep(10000); } 
//		  catch (InterruptedException e) { // TODO
//		  Auto-generated catch block e.printStackTrace(); } });

		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10, threadFactory);
		executor.submit(() -> {
			testDB();
		});

		return "Hello World!";
	}
	@RequestMapping("/sync_delete")
	public String syncDelete() throws InterruptedException {
		Set<String> sourceIds = new HashSet<String>();
		Set<String> targetIds = new HashSet<String>();
		Set<String> deleteIds = new HashSet<String>();
		String result = "";
		//1. get list of ids from source db
		try (Connection conn = DriverManager.getConnection(SOURCE_DB_URL, "hdeflow", "8ik,.lo(");
				Statement stmt = conn.createStatement();
				// execute query for heath check
				ResultSet rs = stmt.executeQuery("SELECT OCMD_ID FROM corporate_customer");) {
				while(rs.next()) {
					sourceIds.add(rs.getString("OCMD_ID"));
				}
			// catch SQLException if any
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		//2. get list of ids from target db
		
		try (Connection conn = DriverManager.getConnection(TARGET_DB_URL, "hspitn02", "SYxo7tcSy7UVwt");
				Statement stmt = conn.createStatement();
				// execute query for heath check
				ResultSet rs = stmt.executeQuery("SELECT OCMD_ID FROM corporate_customer");) {
				while(rs.next()) {
					targetIds.add(rs.getString("OCMD_ID"));
				}
			// catch SQLException if any
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//3. compare the list and find out the deleted ids
		targetIds.removeAll(sourceIds);
		result = "Removed record's IDs: " + String.join(", ", targetIds);
		String[] idsArr = new String[targetIds.size()];
		targetIds.toArray(idsArr);
		//4. delete the record by id on target db
		try (Connection conn = DriverManager.getConnection(TARGET_DB_URL, "hspitn02", "SYxo7tcSy7UVwt");
				Statement stmt = conn.createStatement();
				// execute query for heath check
				) {
			
			for (String ocmdId : targetIds) {
				stmt.executeUpdate("DELETE FROM corporate_customer where OCMD_ID = '"+ ocmdId + "'" );
			}
			 
			// catch SQLException if any
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
    public JSONArray resultSet2JsonArr(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
            .mapToObj(i -> {
                try {
                    return md.getColumnName(i + 1);
                } catch (SQLException e) {

                    e.printStackTrace();
                    return "?";
                }
            })
            .collect(Collectors.toList());

        JSONArray result = new JSONArray();
        while (resultSet.next()) {
            JSONObject row = new JSONObject();
            colNames.forEach(cn -> {
                try {
                    row.put(cn, resultSet.getObject(cn));
                } catch (JSONException | SQLException e) {

                    e.printStackTrace();
                }
            });
            result.put(row);
        }
        return result;
    }
	private void testDB() {
		System.out.println("Hello World!");
		/*
		 * try (Connection conn = DriverManager.getConnection(DB_URL, "hspitn02",
		 * "SYxo7tcSy7UVwt"); Statement stmt = conn.createStatement(); // execute query
		 * for heath check ResultSet rs = stmt.executeQuery(QUERY2);) { // catch
		 * SQLException if any } catch (SQLException e) { e.printStackTrace(); } finally
		 * {
		 * 
		 * }
		 */

	}
	
	class PingAPIResponse implements Serializable{
		private static final long serialVersionUID = 2L;
		Meta meta;
		List<Map<String, String>> data;
		
		PingAPIResponse(){
			meta = new Meta();
			data= new ArrayList<Map<String, String>>();
		}
		public List<Map<String, String>> getData() {
			return data;
		}
		public void setData(List<Map<String, String>> data) {
			this.data = data;
		}
		public Meta getMeta() {
			return meta;
		}
		public void setMeta(Meta meta) {
			this.meta = meta;
		}
		
		
		
	}
	class Meta  implements Serializable{
		private static final long serialVersionUID = 3L;
		Timestamp completedTime;
		public Timestamp getCompletedTime() {
			return completedTime;
		}
		public void setCompletedTime(Timestamp completedTime) {
			this.completedTime = completedTime;
		}
		
		
	}
	public static class DBValidateRequestBody implements Serializable{

		private static final long serialVersionUID = 1L;
		
		String dbName = "";
    	String tableName = "";
    	String fields = "";
    	String condition = "";
    	String expectedResultInJson = "";
		public String getDbName() {
			return dbName;
		}
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public String getFields() {
			return fields;
		}
		public void setFields(String fields) {
			this.fields = fields;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getExpectedResultInJson() {
			return expectedResultInJson;
		}
		public void setExpectedResultInJson(String expectedResultInJson) {
			this.expectedResultInJson = expectedResultInJson;
		}

		
		
	}

}