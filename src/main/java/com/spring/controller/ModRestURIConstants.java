package com.spring.controller;
/**
 * 
 * @author DEEPAK
 *
 */

public class ModRestURIConstants {
    public static final String DUMMY_MOD 		=  "/moderators/dummy";
    public static final String GET_MOD			=  "/moderators/{mod_id}";		//   /moderators/{moderator_id}
    public static final String GET_ALL_MOD 		=  "/moderators";
    public static final String CREATE_MOD 		=  "/moderators";    //       /moderators
    public static final String UPDATE_MOD 		=  "/moderators/{mod_id}";  // /moderators/{moderator_id}
    public static final String DELETE_MOD 		=  "/moderators/delete/{mod_id}";
    
    /*
     * Polls
     */
    public static final String DUMMY_POLL 			=  "/moderators/{mod_id}/dummypoll";
    /*Create a Poll*/
    public static final String CREATE_POLL			=  "/moderators/{mod_id}/polls";  //DONE   // /moderators/{moderator_id}/polls
    /*View a Poll Without Result*/
    public static final String GET_POLL_WO_RESULT 	=  "/polls/{id}";  //DONE  //  /polls/{poll_id}
    /*View a Poll With Result*/
    public static final String GET_POLL 			=  "/moderators/{mod_id}/polls/{Id}"; //DONE   //  /moderators/{moderator_id}/polls/{poll_id}
    /*List All Polls*/
    public static final String GET_ALL_POLL 		=  "/moderators/{mod_id}/polls"; //DONE   //  /moderators/{moderator_id}/polls
    /*delete a poll*/
    public static final String DELETE_POLL 			=  "/moderators/{mod_id}/polls/{id}";   //  /moderators/{moderator_id}/polls/{poll_id}
    /*Vote a Poll*/
    public static final String VOTE_POLL			=  "/polls/{id}";  //"/polls/{id}?choice={choice_index}";  //done
}

/*
 * Changing to Long int
 * */
