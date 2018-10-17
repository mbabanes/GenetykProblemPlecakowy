package plecagene;


public class Participation
{
    private int weight;
    private int worth;
    
    private float weightRate;
    private int worthRate;
    
    private float percent;
    private float worthAmountCumulative;
   
    
    
    
    public Participation()
    {
       worth = 0;
       worthAmountCumulative = 0;
       percent = 0f;
       weight = 0;
       weightRate = 0f;
       worthRate = 0;
    }
    
    

    public int getWorth()
    {
        return worth;
    }

    public void setWorth(int worth)
    {
        this.worth = worth;
    }

    
    public float getWorthAmountCumulative()
    {
        return worthAmountCumulative;
    }

    public void setWorthAmountCumulative(float worthAmountCumulative)
    {
        this.worthAmountCumulative = worthAmountCumulative;
    }

    
    public float getPercent()
    {
        return percent;
    }

    public void setPercent(float percent)
    {
        this.percent = percent;
    }

    
    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public float getWeightRate()
    {
        return weightRate;
    }

    public void setWeightRate(float weightRate)
    {
        this.weightRate = weightRate;
    }

    public int getWorthRate()
    {
        return worthRate;
    }

    public void setWorthRate(int worthRate)
    {
        this.worthRate = worthRate;
    } 
}
