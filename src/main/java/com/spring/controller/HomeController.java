package com.spring.controller;

/**
 * 
 * @author DEEPAK
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
//import javax.validation.Valid;

import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.actuate.autoconfigure.ShellProperties.SimpleAuthenticationProperties.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.net.HttpHeaders;
import com.spring.model.Moderator;
import com.spring.model.Polls;
import com.spring.model.PollsWR;
import com.spring.security.*;


@SuppressWarnings("unused")
@Controller
@ComponentScan
@XmlRootElement
@EnableAutoConfiguration
public class HomeController{
	@Context
	private HttpServletRequest request;
	Date dt=null;
	Map<Integer, Moderator> empData = new HashMap<Integer, Moderator>();
	Map<Integer, Polls> pollData = new HashMap<Integer, Polls>();
	Map<Integer, List<Polls>> pollmodData = new HashMap<Integer, List<Polls>>();
	Map<String, String> userLogins=new HashMap<String,String>();
	
	/*user Logins*/
	String user="foo";
	String passwd="bar";
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	int uniqueModId = 500;
	int uniquePollId = 1000;
	
	/**
	 * Create Unique Moderator Ids
	 * @return
	 */
	public int modUniqueId() {
		uniqueModId = uniqueModId + 1;
		return uniqueModId;
	}
	/**
	 * Create Unique Poll Ids
	 * @return
	 */

	public int pollUniqueId() {
		uniquePollId = uniquePollId + 1;
		return uniquePollId;
	}
	
	/**
	 * default() constructor
	 * @return
	 */
	public HomeController(){
		
		userLogins.put("foo","bar");
	}
	/**
	 * 
	 * @param binder
	 */
	
	
	
	/**
	 * @return: Create Dummy Moderator****************************************************************************
	 */
	
	@RequestMapping(value = ModRestURIConstants.DUMMY_MOD, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Moderator> getDummyEmployee(HttpServletRequest request) {
		Moderator emp = new Moderator();
		ResponseEntity<Moderator> respEntity = null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){
				emp.setId(modUniqueId());
				emp.setName("Dummy");
				emp.setEmailId("dummy@gmail.com");
				emp.setPassword("secret");
				emp.setCreatedDate(dateFormat.format(date));
				empData.put(emp.getId(), emp);
				respEntity=new ResponseEntity<Moderator>(emp,HttpStatus.OK);
			}else{
				respEntity=new ResponseEntity<Moderator>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respEntity;
	}
	
	/**
	 * @Return
	 * Return Moderator using @Param**********************************************************************************
	 */
	
	@RequestMapping(value = ModRestURIConstants.GET_MOD, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Moderator> getModerator(HttpServletRequest request,@PathVariable("mod_id") int empId) {
		Moderator mod=null;
		ResponseEntity<Moderator> respEntity = null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){

				if(empData != null && empData.containsKey(empId)) {
					mod = empData.get(empId);
					respEntity = new ResponseEntity<Moderator>(mod, HttpStatus.OK);
				} else {
					mod = new Moderator();
					respEntity = new ResponseEntity<Moderator>(mod, HttpStatus.NO_CONTENT);
				}
			}else{
				respEntity=new ResponseEntity<Moderator>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respEntity;	
	}

	@RequestMapping(value = ModRestURIConstants.GET_ALL_MOD, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Moderator>> getAllModerators() {
		List<Moderator> mods = new ArrayList<Moderator>();
    	ResponseEntity<List<Moderator>> respEntity = null;
    	if(empData != null) {
			Set<Integer> modIdKeys = empData.keySet();
			for (Integer i : modIdKeys) {
				mods.add(empData.get(i));
			}
			respEntity=new ResponseEntity<List<Moderator>>(mods, HttpStatus.OK);
    	} else {
    		mods = new ArrayList<Moderator>();
    		respEntity = new ResponseEntity<List<Moderator>>(mods, HttpStatus.NO_CONTENT);
    	}
    	return respEntity;
	}

	
	@RequestMapping(value = ModRestURIConstants.CREATE_MOD, consumes = {"text/plain","application/json"}, produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Moderator> createModerator(@Valid @RequestBody Moderator mod, BindingResult bindingResult, HttpServletRequest request ) {
		ResponseEntity<Moderator> respEntity = null;
		if(bindingResult.hasErrors()){
			return respEntity = new ResponseEntity<Moderator>(HttpStatus.BAD_REQUEST);
		}else{
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){
				int intId = modUniqueId();
				mod.setId(intId);
				mod.setCreatedDate(dateFormat.format(date));
				empData.put(intId, mod);
				respEntity = new ResponseEntity<Moderator>(mod, HttpStatus.CREATED);
			}else{
				respEntity=new ResponseEntity<Moderator>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		}
		return respEntity;
	}

	@RequestMapping(value = ModRestURIConstants.UPDATE_MOD, produces = { "application/json" }, consumes={"application/json"}, method = RequestMethod.PUT)
	public ResponseEntity<Moderator> updateModerator(HttpServletRequest request,@Valid @RequestBody  Moderator emp,BindingResult bindingResult, @PathVariable("mod_id") int modId) {
		ResponseEntity<Moderator> respEntity = null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			Moderator empTemp=null;
			if(authorised){
				if(bindingResult.hasErrors()){
					return new ResponseEntity<Moderator>(emp, HttpStatus.BAD_REQUEST);
				}else{
					if(empData != null && empData.containsKey(modId)) {
						empTemp = empData.get(modId);
						if (emp.getName() != null) {
							empTemp.setName(emp.getName());
						}
							empTemp.setId(modId);
						
						if (emp.getEmailId() != null) {
							empTemp.setEmailId(emp.getEmailId());
						}
						if (emp.getPassword() != null) {
							empTemp.setPassword(emp.getPassword());
						}
						if (emp.getCreatedDate() != null) {
							empTemp.setCreatedDate(emp.getCreatedDate());
						}
						empData.put(modId, empTemp);
						respEntity = new ResponseEntity<Moderator>(empTemp, HttpStatus.CREATED);
					} else {
						empTemp = new Moderator();
						respEntity = new ResponseEntity<Moderator>(empTemp,HttpStatus.NO_CONTENT);
					}
				}
			}else{
				respEntity=new ResponseEntity<Moderator>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return respEntity;
	}
	
/*
 * @Delete the Moderator**********************************************************************************************
 */
	@RequestMapping(value = ModRestURIConstants.DELETE_MOD, produces = { "application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Moderator> deleteModerator(HttpServletRequest request, @PathVariable("id") int empId) {
		Moderator emp=null;
		ResponseEntity<Moderator> respEntity=null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			Moderator empTemp=null;
			if(authorised){
				emp = empData.get(empId);
				empData.remove(empId);
			}else{
				respEntity=new ResponseEntity<Moderator>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respEntity;
	}
	
	/*
	 * Polls Mapping****************************************************************************************************
	 */
	@RequestMapping(value = ModRestURIConstants.DUMMY_POLL, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Polls> getPolls(@PathVariable("mod_id") int mod_Id, HttpServletRequest request) {
		Polls poll = new Polls();
		String choice="Android,Iphone";
		poll.setId(pollUniqueId());
		ResponseEntity<Polls> respEntity=null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){
				String[] choices = choice.split(",");
				poll.setQuestion("What type of smartphone do you have?");
				poll.setStartat("2015-02-23T13:00:00.000Z");
				poll.setExpiredat("2015-02-23T13:00:00.000Z");
				poll.setChoice(choices);
				pollData.put(poll.getId(), poll);
				List<Polls> pollsList =  pollmodData.get(mod_Id);
				if(pollsList != null && !pollsList.isEmpty()) {
					pollsList.add(poll);
				} else {
					pollsList = new ArrayList<Polls>();
					pollsList.add(poll);
				}
				pollmodData.put(mod_Id, pollsList);
		}else{
			respEntity=new ResponseEntity<Polls>(HttpStatus.UNAUTHORIZED);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respEntity;
	}
	
	
	
	@RequestMapping(value = ModRestURIConstants.CREATE_POLL,  method = RequestMethod.POST, produces={"application/json"})
	public ResponseEntity<PollsWR> createPolls(@Valid @RequestBody Polls polls, BindingResult bindingresult, @PathVariable("mod_id") int mod_Id, HttpServletRequest request) {
		ResponseEntity<PollsWR> respEntity=null;
		if(bindingresult.hasErrors()){
			return respEntity = new ResponseEntity<PollsWR>(HttpStatus.BAD_REQUEST);
		}else{
			polls.setId(pollUniqueId());
			polls.setResults(new int [2]);
			pollData.put(polls.getId(), polls);
			List<Polls> pollsList =  pollmodData.get(mod_Id);
			if(pollsList != null && !pollsList.isEmpty()) {
				pollsList.add(polls);
			} else {
				pollsList = new ArrayList<Polls>();
				pollsList.add(polls);
			}
			pollmodData.put(mod_Id, pollsList);
			PollsWR pollsWR=new PollsWR();
			if(pollData != null) {
					pollsWR.setId(polls.getId());
					pollsWR.setQuestion(polls.getQuestion());
					pollsWR.setChoice(polls.getChoice());
					pollsWR.setExpiredat(polls.getExpiredat());
					pollsWR.setStartat(polls.getStartat());
			 respEntity = new ResponseEntity<PollsWR>(pollsWR, HttpStatus.CREATED);
			}
		}
		return respEntity;
	}

	@RequestMapping(value = ModRestURIConstants.GET_POLL, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Polls> getPoll(HttpServletRequest request, @PathVariable("mod_id") int mod_Id, @PathVariable("Id") int pollId) {
		ResponseEntity<Polls> respEntity=null;
		List<Polls> onlyPollData = new ArrayList<Polls>();
		if(pollmodData.containsKey(mod_Id)){
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){
				
				onlyPollData = pollmodData.get(mod_Id);
				for(Polls poll : onlyPollData) {
					if(poll.getId() == pollId) {
						respEntity = new ResponseEntity<Polls>(poll, HttpStatus.OK);
					}
				}
				}else{
					respEntity=new ResponseEntity<Polls>(HttpStatus.NOT_FOUND);
				}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		}else{
			respEntity=new ResponseEntity<Polls>(HttpStatus.NOT_FOUND);
		}
		return respEntity;
    }
	
	@RequestMapping(value = ModRestURIConstants.GET_ALL_POLL, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Polls>> getAllPoll(HttpServletRequest request, @PathVariable("mod_id") int mod_Id) {
		List<Polls> onlyPollData = new ArrayList<Polls>();
		ResponseEntity<List<Polls>> respEntity=null;
		try{
			Authentications auth=new Authentications();
			Boolean authorised=auth.checkAuthorization(request);
			if(authorised){
				onlyPollData = pollmodData.get(mod_Id);
				respEntity = new ResponseEntity<List<Polls>>(onlyPollData, HttpStatus.OK);
			}else{
				respEntity=new ResponseEntity<List<Polls>>(HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respEntity;
    }


	@RequestMapping(value = ModRestURIConstants.GET_POLL_WO_RESULT, produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<PollsWR> getPollsWOResults(@PathVariable("id") int poll_Id) {
		Polls polls=new Polls();
		PollsWR pollsWR=new PollsWR();
		ResponseEntity<PollsWR> respEntity=null;
		polls=pollData.get(poll_Id);
		if(pollData != null) {
				pollsWR.setId(polls.getId());
				pollsWR.setQuestion(polls.getQuestion());
				pollsWR.setChoice(polls.getChoice());
				pollsWR.setExpiredat(polls.getExpiredat());
				pollsWR.setStartat(polls.getStartat());
				respEntity = new ResponseEntity<PollsWR>(pollsWR, HttpStatus.OK);
			} else {
				pollsWR = new PollsWR();
				respEntity = new ResponseEntity<PollsWR>(pollsWR, HttpStatus.NO_CONTENT);
			}
		return respEntity;
	}

	@RequestMapping(value = ModRestURIConstants.VOTE_POLL, produces = { "application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Polls> getVoteResults(HttpServletRequest request, @PathVariable("id") int pollId) {
		ResponseEntity<Polls> respEntity =null;
		Polls polls=new Polls();
			String choiceIndex = request.getParameter("choice");
			Polls poll = pollData.get(pollId);
			int[] res = poll.getResults();
			if (Integer.valueOf(choiceIndex).intValue() == 0) {
				res[0] = res[0] + 1;
			}
			if (Integer.valueOf(choiceIndex).intValue() == 1) {
			res[1] = res[1] + 1;
			}
			poll.setResults(res);
			respEntity=new ResponseEntity<Polls>(polls, HttpStatus.NO_CONTENT);
	return respEntity;
	}

		
   	@RequestMapping(value = ModRestURIConstants.DELETE_POLL, produces = { "application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Polls> deletePoll(@PathVariable("mod_id") int modId,@PathVariable("id") int pollId) {
   		List<Polls> onlyPollData = pollmodData.get(modId);
    	ResponseEntity<Polls> respEntity=null;
   		if(pollmodData.containsKey(modId)){
   				for(Polls poll : onlyPollData) {
					if(poll.getId() == pollId) {
						onlyPollData.remove(poll);
						break;
					}
   				}
   				respEntity=new ResponseEntity<Polls>(HttpStatus.NO_CONTENT);
   			}else{
   				return new ResponseEntity<Polls>(HttpStatus.NOT_FOUND);
   			}
		return respEntity;
	}
}
