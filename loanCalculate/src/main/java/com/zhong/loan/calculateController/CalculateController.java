package com.zhong.loan.calculateController;

import com.zhong.loan.calculateService.CalculateService;
import com.zhong.loan.pojo.Interest;
import com.zhong.loan.pojo.Loan;
import com.zhong.loan.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculateController {

    @Autowired
    private CalculateService calculateService;

    @RequestMapping("/")
    public String calculate(){
        return "calculate";
    }

    @PostMapping("/getInterest")
    @ResponseBody
    public List<Interest> getInterest(@RequestBody Loan loan){
//        System.out.println(loan.getDayLPR());
        List<Interest> list = new ArrayList<>();
        list.add(null);
        if(LoanUtils.isLegal(loan) == 0) return list;
        String style = loan.getInsterestStyle();
        switch (style) {
            case "month":
                return calculateService.month(loan);
            case "season":
                return calculateService.season(loan);
            case "year":
                return calculateService.year(loan);
        }
        return list;
    }
}
