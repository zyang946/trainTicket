package com.rm2pt.generator.springboot.trainTicket.service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rm2pt.generator.springboot.trainTicket.entity.*;
import com.rm2pt.generator.springboot.trainTicket.utils.DaoManage;
import com.rm2pt.generator.springboot.trainTicket.utils.PreconditionException;
import com.rm2pt.generator.springboot.trainTicket.utils.ServiceManage;
import com.rm2pt.generator.springboot.trainTicket.utils.StandardOPs;
import com.rm2pt.generator.springboot.trainTicket.redis.CurrentUtils;

@Transactional
@Service
public class ManageAccoutCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createAccout(String accoutid,String name,String phonenumber) throws PreconditionException{
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutid);
		
		if(StandardOPs.oclIsUndefined(accout) == true)
		{
			Accout acc = new Accout();
			acc.setAccoutId(accoutid);
			acc.setName(name);
			acc.setPhoneNumber(phonenumber);
			DM.getAccoutDao().save(acc);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Accout queryAccout(String accoutid) throws PreconditionException{
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutid);
		
		if(StandardOPs.oclIsUndefined(accout) == false)
		{
			return accout;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifyAccout(String accoutid,String name,String phonenumber) throws PreconditionException{
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutid);
		
		if(StandardOPs.oclIsUndefined(accout) == false)
		{
			accout.setAccoutId(accoutid);
			accout.setName(name);
			accout.setPhoneNumber(phonenumber);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteAccout(String accoutid) throws PreconditionException{
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutid);
		
		if(StandardOPs.oclIsUndefined(accout) == false && accout!= null)
		{
			DM.getAccoutDao().delete(accout);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
				
	public static Object GetData(Optional<?> op) {
		if (op.isPresent())
			return op.get();
		else 
			return null;
	}
}
