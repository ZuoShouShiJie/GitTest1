import java.math.BigDecimal;

/**
 * Created by daixiaohu on 2018/3/6.
 */
public class MoneyUtils {
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     *
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     * @param invest
     *            总借款额（贷款本金）
     * @param monthRate
     *            月利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还利息
     */
    public static  BigDecimal getPerMonthInterest(BigDecimal invest, double monthRate, int totalmonth, int i) {
        BigDecimal monthInterest;
            BigDecimal multiply = invest.multiply(new BigDecimal(monthRate));
            BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, totalmonth)).subtract(new BigDecimal(Math.pow(1 + monthRate, i-1)));
            monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 6, BigDecimal.ROUND_DOWN);
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN);
        return monthInterest;
    }

    public static  BigDecimal getAllMonthInterest(BigDecimal invest, double monthRate, int period) {
        BigDecimal allInterest = new BigDecimal(0);
        for (int i=1;i<period+1;i++){
            BigDecimal monthInterest;
            BigDecimal multiply = invest.multiply(new BigDecimal(monthRate));
            BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, period)).subtract(new BigDecimal(Math.pow(1 + monthRate, i-1)));
            monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, period) - 1), 6, BigDecimal.ROUND_DOWN);
            allInterest = allInterest.add(monthInterest.setScale(2, BigDecimal.ROUND_DOWN));
        }

        return allInterest;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param monthRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还本金
     */
    public static  BigDecimal getPerMonthPrincipal(BigDecimal invest, String monthRate, int totalmonth,int i) {
        BigDecimal monthIncome = invest
                .multiply(new BigDecimal(Double.valueOf(monthRate) * Math.pow(1 + Double.valueOf(monthRate), totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + Double.valueOf(monthRate), totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);
         BigDecimal mapInterest = getPerMonthInterest(invest, Double.valueOf(monthRate), totalmonth, i);
            BigDecimal principal =   monthIncome.subtract(mapInterest);
        return principal;
    }


    /**
     * 等额本金
     * 每期应该本金。每期本金一样
     * @param invest
     * @param totalmonth
     * @return
     *
     * invest:贷款总金额；totalmonth:期数；
     * 每期应还本金=invest/totalmonth
     *
     */
 public static BigDecimal getPerAmount(BigDecimal invest,int totalmonth){
     return invest.divide(new BigDecimal(totalmonth),2,BigDecimal.ROUND_UP);//每期应还本金
 }

/**
 * 等额本金
 * 每期应还利息
 * invest:贷款总金额；totalmonth:期数；i:当期第几期；monthRate:月利率；
 * 每期应还款利息=(invest-(invest/totalmonth)*(i-1))*monthRate
 */
public static BigDecimal getPerInterest(BigDecimal invest,String monthRate,int totalmonth,int i){
    BigDecimal perAmount = invest.divide(new BigDecimal(totalmonth),3,BigDecimal.ROUND_UP);//每期应还本金
    BigDecimal perInterest = (invest.subtract(perAmount.multiply(new BigDecimal(i-1)))).multiply(new BigDecimal(monthRate)).setScale(2,BigDecimal.ROUND_UP);
    return perInterest;
}

}
