package plecagene;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class Logic
{
    private Map<Integer, Item> items;
    private int itemsCount;
    
    private boolean[][] population;
    private int maxWeight;
    private Map<Integer, Participation> participation;
    
    
    
    public Logic()
    {
        //maxWeight = 25;
        
        this.createItems();
        
        itemsCount = items.size();
        
        population = new boolean[8][itemsCount];
        
        
        this.participationInit();
       
    }
    
    public void setMaxWeight(int maxWeight)
    {
        this.maxWeight = maxWeight;
    }
    
    public void start(int generations, int view)
    {
        this.chooseRandomPopulation(); 
        this.countParticipation();
        
        System.out.println("Zaczynam od: ");
        this.showSomething();
        
        this.selection();
        
        for (int i = 0; i < generations; i++)
        {
            this.operations();
            this.countParticipation();
            
            if( view != 0)
            {
                if (  ( ( ( i + 1 ) % view) == 0) || ( i == 0 ) || ( i == (generations - 1) ) )
                {
                    System.out.println("\n===============================================================================\n");
                    System.out.println("P o k o l e n i e   " + i);
                    this.showSomething();
                }
            }
            else
            {
                if ( ( i == 0 ) || ( i == (generations - 1) ) )
                {
                    System.out.println("\n===============================================================================\n");
                    System.out.println("P o k o l e n i e   " + i);
                    this.showSomething();
                }       
            }
            
            this.selection();
        }
        
        this.countParticipation();
        System.out.println("\n=======================================================================================================================\n");
        System.out.println("W y n i k   o s t a t e c z n y :");
        this.showSomething();
    }
    
    private void createItems()
    {
        items = new TreeMap<>();
        
        items.put(0, new Item(10, 5, "Przedmiot_1"));
        items.put(1, new Item(5, 3, "Przedmiot_2"));
        items.put(2, new Item(2, 7, "Przedmiot_3"));
        items.put(3, new Item(8, 17, "Przedmiot_4"));
        items.put(4, new Item(3, 12, "Przedmiot_5"));
        items.put(5, new Item(1, 7, "Przedmiot_6"));
        items.put(6, new Item(6, 4, "Przedmiot_7"));
        
        
    }
    
    private void participationInit()
    {
        participation = new TreeMap();
        for(int i = 0; i < population.length; i++)
        {
            participation.put( i, new Participation() );
        }
    }
    
    
    
    private void chooseRandomPopulation()
    {
        Random rand = new Random();
        
        for(int i = 0; i < population.length; i++)
        {
            for (int j = 0; j < population[i].length; j++)
            {
                population[i][j] = rand.nextBoolean();
            }
        }
    }
    
    
    
    
    public void countParticipation()
    {
        for(int chrom = 0; chrom < population.length; chrom++)
        {
            for(int gen = 0; gen < population[chrom].length; gen++)
            {
                this.countWeightOfChromo(chrom);
                this.countWorthOfChromo(chrom);
            }
        }
        
        this.countWeightRate();
        this.countWorthRate();
        this.countPercentOfUseageWorthRateAndWorthAmountCumulative();
    }
    
    
    public void selection()
    {
        Random rand = new Random();
        
        float[] numbersToSelect = new float[population.length];
        
        for (int i = 0; i < numbersToSelect.length; i++)
        {
            numbersToSelect[i] = rand.nextFloat() - 0.03f;
           
        }
        
        boolean[][] tmpPopulation = new boolean[population.length][population[0].length];
        
        for(int i = 0; i < numbersToSelect.length; i++)
        {
            for(Integer chrom: participation.keySet())
            {
                if (participation.get(chrom).getWorthAmountCumulative() > numbersToSelect[i])
                {
                    tmpPopulation[i] = Arrays.copyOf(population[chrom], population[chrom].length);
                    break;
                }
            }
        }
        
        population = Arrays.copyOf(tmpPopulation, tmpPopulation.length);
    }
    
    
    
    public void operations()
    {
        byte[] operation = {1, 1, 1, 1, 1, 0, 2, 2, 2, 2};
        
        Random rand = new Random();
        
        for(int chrom = 0; chrom < population.length; chrom++)
        {
            int lotteredOperation = rand.nextInt(10);
            
            switch( operation[lotteredOperation] )
            {
                case 0:
                {
                    this.mutation(chrom);
                    break;
                }
                
                case 1:
                {
                    this.cross(chrom);
                    break;
                }
            }
        }
        
    }
    
    
    
    public void showPopulation()
    {
       System.out.println("Populacja:");
        for (int chrom = 0; chrom < population.length; chrom++)
        {
            System.out.print("Chromosom: " + chrom + "  ");
            for(int gen = 0; gen < population[chrom].length; gen++)
            {
                if (population[chrom][gen])
                {
                    System.out.print("1 ");
                }
                else
                {
                    System.out.print("0 ");
                }
            }
            System.out.println("\n");
        }
    }
    
    private void countWeightRate()
    {
        for (Integer chrom : participation.keySet())
        {
            float weightRate;
            
            weightRate = ( (float) participation.get(chrom).getWeight() / this.maxWeight) + 0.0f;
            
            participation.get(chrom).setWeightRate(weightRate);
        }
    }
    
    
    private void countWorthRate()
    {
        for(Integer chrom : participation.keySet())
        {
            int worthRate;
            
            if ( participation.get(chrom).getWeightRate() <= 1f )
            {
                worthRate = participation.get(chrom).getWorth() * 2;
            }
            else
            {
                worthRate = participation.get(chrom).getWorth() / 2;
            }
            
            participation.get(chrom).setWorthRate(worthRate);
        }
    }
    
    
    private void countPercentOfUseageWorthRateAndWorthAmountCumulative()
    {
        int amountWorthOfChromo = 0;
        
        for(Integer chrom : participation.keySet() )
        {
            amountWorthOfChromo += participation.get(chrom).getWorthRate();
        }
        
        float amount = amountWorthOfChromo + 0.0f;
        
        for(Integer chrom : participation.keySet() )
        {
            participation.get(chrom).setPercent( (float)(participation.get(chrom).getWorthRate() / amount) );
            
            if (chrom != 0)
            {
                float amountWorthCumulative = participation.get(chrom - 1).getWorthAmountCumulative();
                       
                amountWorthCumulative += participation.get(chrom).getPercent();
                       
                participation.get(chrom).setWorthAmountCumulative(amountWorthCumulative);        
            }
            else
            {
                participation.get(chrom).setWorthAmountCumulative(participation.get(chrom).getPercent());
            }
        } 
    }
    
    
   
    
    private void countWeightOfChromo(int chrom)
    {
        int amountWeight = 0;
        
        for (int gen = 0; gen < population[chrom].length; gen++)
        {
            if (population[chrom][gen])
            {
                amountWeight += items.get(gen).getWeight();
            }
        }
        
        participation.get(chrom).setWeight(amountWeight);
    }
    
    
    private void countWorthOfChromo(int chrom)
    {
        int worth = 0;
        for (int gen = 0; gen < population[chrom].length; gen++)
        {
            if (population[chrom][gen])
            {
                worth += items.get(gen).getWorth();
            }
        }
        
        participation.get(chrom).setWorth( worth );   
    }
    
    
    
    
    public void mutation(int chromToMutation)
    {
        Random rand = new Random();
        
        int genToMutation = rand.nextInt(population[chromToMutation].length);
        
        if( population[chromToMutation][genToMutation] )
        {
            population[chromToMutation][genToMutation] = false;
        }
        else
        {
            population[chromToMutation][genToMutation] = true;
        }
    }
    
    
    public void cross(int chromToReplace)
    {
       Random rand = new Random();
        
       int dad = lotteryDad(chromToReplace);
       int mom = lotteryMom(dad, chromToReplace);
       
    
       int cutPoint = rand.nextInt( (population[0].length - 2) ) + 1;
       boolean[] newChrom = new boolean[population[0].length];
       
       for(int gen = 0; gen < cutPoint; gen++)
       {
           newChrom[gen] = population[dad][gen];
       }
        
       for(int gen = cutPoint; gen < population[0].length; gen++)
       {
           newChrom[gen] = population[mom][gen];
       }
        
       population[chromToReplace] = Arrays.copyOf(newChrom, newChrom.length);
    }
    
    
    
    
    
    
    private int lotteryDad(int chromToReplace)
    {
        Random rand = new Random();
        
        int dad;
        
        do
        {
            dad = rand.nextInt(population.length);
        }while(dad == chromToReplace) ;
        
        return dad;
    }
    
    private int lotteryMom(int dad, int chromToReplace)
    {
        Random rand = new Random();
        
        int mom;
        
        do
        {
            mom = rand.nextInt(population.length);
            
        }while( (mom == dad) || (mom == chromToReplace) );
        
        return mom;
    }
    
    
    
    
    
    public void showItems()
    {
        for (Integer i : items.keySet())
        {
            System.out.println( "Element: " + i + " Nazwa: " + items.get(i).getName() + " Waga: " + items.get(i).getWeight() + " Wartosc: " + items.get(i).getWorth() + "\n" );
        }
    }
    
    public void showSomething()
    {
        
        
        System.out.println("\nPopulacja:");
        
        for (int chrom = 0; chrom < population.length; chrom++)
        {
            System.out.print("Chromosom: " + chrom + "  ");
            for(int gen = 0; gen < population[chrom].length; gen++)
            {
                if (population[chrom][gen])
                {
                    System.out.print("1 ");
                }
                else
                {
                    System.out.print("0 ");
                }
            }
            System.out.println("\n");
        }
        
        
        System.out.println();
        System.out.println("Przystosowanie:");
        
        for(Integer chrom : participation.keySet())
        {
            System.out.print("Chromosom: " + chrom + " laczna wartosc: " + participation.get(chrom).getWorth());
            System.out.print(" laczna masa: " + participation.get(chrom).getWeight());
            System.out.print(" wspolczynnik masy: " + participation.get(chrom).getWeightRate());
            System.out.print(" wartosc przystosowania: " + participation.get(chrom).getWorthRate());
            System.out.print(" udzial przystosowania: " + participation.get(chrom).getPercent());
            System.out.print(" udzial narastajaco: " + participation.get(chrom).getWorthAmountCumulative());
            
            System.out.println("\n");
        }
    }
}
