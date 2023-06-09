package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {

    private final int PROCESS_TIME = 5;
    private ArrayList<Process> inQueue, currentList;
    private ArrayList<PartitionReport> spaceList,processList,readyList,newInqueue, dispatchList, executionList, expirationList, finishedList, finishedPartition;
    private ArrayList<Partition> partitions;
    private ArrayList<Condensation> condensations;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
        this.inQueue = new ArrayList<>();
        this.currentList = new ArrayList<>();
        this.readyList = new ArrayList<>();
        this.newInqueue = new ArrayList<>();
        this.dispatchList = new ArrayList<>();
        this.executionList = new ArrayList<>();
        this.expirationList = new ArrayList<>();
        this.finishedPartition = new ArrayList<>();
        this.finishedList = new ArrayList<>();
        this.processList = new ArrayList<>();
        this.condensations = new ArrayList<>();
        this.spaceList = new ArrayList<>();
    }

    public boolean isAlreadyProcessName(String name){
        for(Process process: inQueue){
            if(process.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addToInQueue(Process process){
        this.inQueue.add(process);
    }

    public Object[][] getProcessListAsMatrixObject(ArrayList<Process> list){
        return this.parseArrayListToMatrixObject(list);
    }

    private Object[][] parseArrayListToMatrixObject(ArrayList<Process> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getTime();
            processList[i][2] = list.get(i).getSize();
        }
        return processList;
    }

    public Object[][] getProcessListAsMatrixReportObject(ArrayList<PartitionReport> list){
        return this.parseArrayListToMatrixReportObject(list);
    }

    private Object[][] parseArrayListToMatrixReportObject(ArrayList<PartitionReport> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getPartitionName();
            processList[i][1] = list.get(i).getProcess().getName();
            processList[i][2] = list.get(i).getProcess().getTime();
            processList[i][3] = list.get(i).getProcess().getSize();
        }
        return processList;
    }

    public Object[][] getProcessListAsMatrixReportCon(ArrayList<Condensation> list){
        return this.parseArrayListToMatrixReportCon(list);
    }

    private Object[][] parseArrayListToMatrixReportCon(ArrayList<Condensation> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getSize();
            processList[i][2] = list.get(i).getInitSize();
            processList[i][3] = list.get(i).getFinishSize();
        }
        return processList;
    }

    public Process getProcessInQueue(int indexDataInTable) {
        return this.inQueue.get(indexDataInTable);
    }

    public void updateProcessInQueue(Process newProcess, int indexDataInTable) {
        this.inQueue.set(indexDataInTable, newProcess);
    }
    public void deleteProcessInQueue(int indexDataInTable) {
        this.inQueue.remove(indexDataInTable);
    }

    public ArrayList<Process> getInQueue() {
        return inQueue;
    }

    public void setInQueue(ArrayList<Process> inQueue) {
        this.inQueue = inQueue;
    }

    public ArrayList<Process> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(ArrayList<Process> currentList) {
        this.currentList = currentList;
    }

    public ArrayList<PartitionReport> getReadyList() {
        return readyList;
    }

    public void setReadyList(ArrayList<PartitionReport> readyList) {
        this.readyList = readyList;
    }

    public ArrayList<PartitionReport> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(ArrayList<PartitionReport> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public ArrayList<PartitionReport> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(ArrayList<PartitionReport> executionList) {
        this.executionList = executionList;
    }

    public ArrayList<PartitionReport> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(ArrayList<PartitionReport> expirationList) {
        this.expirationList = expirationList;
    }

    public ArrayList<PartitionReport> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<PartitionReport> finishedList) {
        this.finishedList = finishedList;
    }

    public ArrayList<PartitionReport> getFinishedPartition() {
        return finishedPartition;
    }

    public void setFinishedPartition(ArrayList<PartitionReport> finishedPartition) {
        this.finishedPartition = finishedPartition;
    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(ArrayList<Partition> partitions) {
        this.partitions = partitions;
    }

    public ArrayList<PartitionReport> getProcessList() {
        return processList;
    }

    public void setProcessList(ArrayList<PartitionReport> processList) {
        this.processList = processList;
    }

    public ArrayList<PartitionReport> getSpaceList() {
        return spaceList;
    }

    public void setSpaceList(ArrayList<PartitionReport> spaceList) {
        this.spaceList = spaceList;
    }

    public ArrayList<Condensation> getCondensations() {
        return condensations;
    }

    public void setCondensations(ArrayList<Condensation> condensations) {
        this.condensations = condensations;
    }

    public ArrayList<PartitionReport> getNewInqueue() {
        return newInqueue;
    }

    public void setNewInqueue(ArrayList<PartitionReport> newInqueue) {
        this.newInqueue = newInqueue;
    }

    public void cleanAllLists() {
        this.inQueue.clear();
        this.currentList.clear();
        this.readyList.clear();
        this.dispatchList.clear();
        this.executionList.clear();
        this.expirationList.clear();
        this.finishedList.clear();
        this.finishedPartition.clear();
        this.partitions.clear();
        this.newInqueue.clear();
        this.processList.clear();
        this.condensations.clear();
        this.spaceList.clear();
    }

    public void initSimulation(){
        //this.generateCondensations();
        this.init();
        this.initNewInQueue();
        this.copyToCurrentProcess();
        this.initLoadToReady();
        for (int i = 0; i < readyList.size(); i++) {
            this.loadToDispatchQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), readyList.get(i).getProcess().getTime(), readyList.get(i).getProcess().getSize())));
            if(readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0){
                this.loadToExecQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));

            }else{
                this.loadToExecQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), readyList.get(i).getProcess().getTime(), readyList.get(i).getProcess().getSize())));
            }
            if (!(readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0)) {
                if (readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1) {
                    this.loadToExpirationQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));
                    this.loadToReadyQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));
                } else {
                    this.loadToFinishedQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), BigInteger.valueOf(0), readyList.get(i).getProcess().getSize())));
                }

            }
        }
    }

    private void generateCondensations(){
        ArrayList<Process> newList = new ArrayList<>(inQueue);
        while(this.areZeros(newList)){
            for(int i = 0; i < newList.size(); i++){
                if(newList.get(i).getTime().compareTo(new BigInteger("0")) == 1){
                    int value = newList.get(i).getTime().intValue() - PROCESS_TIME;
                    if(value <= 0)
                        value = 0;
                    newList.get(i).setTime(new BigInteger(String.valueOf(value)));
                }
            }
            this.verifyContiguous(newList);
        }
    }

    private boolean areZeros(ArrayList<Process> newList) {
        for(Process process : newList){
            if(process.getTime().compareTo(new BigInteger("0")) == 1) return true;
        }
        return false;
    }

    private void verifyContiguous(ArrayList<Process> newList) {
        for(int i = 0; i < newList.size() - 1; i++){
            if(newList.get(i).getTime().compareTo(new BigInteger("0")) == 0){
                 if(newList.get(i - 1) != null && newList.get(i + 1) != null){
                    if(newList.get(i - 1).getTime().equals(BigInteger.ZERO)){

                    }

                }
                else if(newList.get(i -1) == null){

                }
                else if(newList.get(i + 1) == null){

                }
            }
        }
    }

    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void initNewInQueue(){
        for (int i = 0; i < inQueue.size(); i++) {
            newInqueue.add(new PartitionReport("part"+String.valueOf(i+1), inQueue.get(i)));
        }
    }
    private void initLoadToReady() {
        readyList.addAll(newInqueue);
    }


    //Método para pruebas
    private void init() {
        readyList.addAll(newInqueue);
        inQueue.add(new Process("p1", new BigInteger("10"), new BigInteger("10")));
        inQueue.add(new Process("p2", new BigInteger("5"), new BigInteger("20")));
        inQueue.add(new Process("p3", new BigInteger("15"), new BigInteger("15")));
        inQueue.add(new Process("p4", new BigInteger("4"), new BigInteger("5")));


        /*inQueue.add(new Process("p1", new BigInteger("5"), new BigInteger("10")));
        inQueue.add(new Process("p2", new BigInteger("5"), new BigInteger("20")));
        inQueue.add(new Process("p3", new BigInteger("15"), new BigInteger("15")));
        inQueue.add(new Process("p4", new BigInteger("5"), new BigInteger("5")));
        inQueue.add(new Process("p5", new BigInteger("5"), new BigInteger("5")));*/


        /*inQueue.add(new Process("p1", new BigInteger("10"), new BigInteger("10")));
        inQueue.add(new Process("p2", new BigInteger("5"), new BigInteger("20")));
        inQueue.add(new Process("p3", new BigInteger("15"), new BigInteger("15")));
        inQueue.add(new Process("p4", new BigInteger("4"), new BigInteger("5")));
        inQueue.add(new Process("p5", new BigInteger("5"), new BigInteger("5")));
        inQueue.add(new Process("p6", new BigInteger("10"), new BigInteger("10")));
        inQueue.add(new Process("p7", new BigInteger("40"), new BigInteger("20")));
        inQueue.add(new Process("p8", new BigInteger("60"), new BigInteger("30")));
        inQueue.add(new Process("p9", new BigInteger("5"), new BigInteger("40")));
        inQueue.add(new Process("p10", new BigInteger("16"), new BigInteger("5")));
        inQueue.add(new Process("p11", new BigInteger("6"), new BigInteger("12")));
        inQueue.add(new Process("p12", new BigInteger("8"), new BigInteger("22")));*/
    }

    public void copyToCurrentProcess(){
        currentList.addAll(inQueue);
    }

    private void loadToReadyQueue(PartitionReport process) {
        this.readyList.add(process);
        System.out.println("Listos: ");
        System.out.println(readyList.toString());
    }

    private void loadToProcessList(PartitionReport process) {
        this.processList.add(process);
        System.out.println("space: ");
        System.out.println(processList.toString());
    }
    private void loadToDispatchQueue(PartitionReport partitionReport) {
        this.dispatchList.add(partitionReport);
        System.out.println("Despachados: " + dispatchList.toString());
    }
    private void loadToExecQueue(PartitionReport process) {
        this.executionList.add(process);
        System.out.println("Ejecución: " + executionList.toString());
    }
    private void loadToExpirationQueue(PartitionReport process) {
        this.expirationList.add(process);
        System.out.println("Expiración: ");
        System.out.println(expirationList.toString());
    }
    private void loadToFinishedQueue(PartitionReport process) {
        this.finishedList.add(process);
        System.out.println("Finalizados: " + finishedList.toString());
    }

    private void loadToCondensations(Condensation process) {
        this.condensations.add(process);
    }

    public BigInteger findMaxTime(){
        BigInteger num = new BigInteger("0");
        BigInteger num1 = new BigInteger("0");
        for (int i = 0; i < newInqueue.size(); i++) {
            if(newInqueue.get(i).getProcess().getTime().compareTo(num)==1){
                num = newInqueue.get(i).getProcess().getTime();
            }
        }
        num1 = (num.divide(BigInteger.valueOf(PROCESS_TIME))).multiply(BigInteger.valueOf(newInqueue.size()));
        num1 = num1.subtract(BigInteger.valueOf(1));
        return num1;
    }


    public void addCondensations(){
        int s=0;
        int s1=0;
        this.iniSpace();
        int count =0;
        int count1 =0;
        for (int i = 0; i <= findMaxTime().intValue(); i++) {
            if(processList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || processList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0){
                this.loadToProcessList(new PartitionReport(processList.get(i).getPartitionName(),new Process(processList.get(i).getProcess().getName(), processList.get(i).getProcess().getTime().subtract(new BigInteger("5")), processList.get(i).getProcess().getSize())));
            }else{
                this.loadToProcessList(new PartitionReport(processList.get(i).getPartitionName(),new Process(processList.get(i).getProcess().getName(),new BigInteger("0"), processList.get(i).getProcess().getSize())));
            }
            count ++;
            if (count == newInqueue.size()){
                for (int j = i+1; j < processList.size(); j++) {
                    System.out.println(j + "j");
                    System.out.println(processList.get(j));
                    if(processList.get(j).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0){
                        s+=processList.get(j).getProcess().getSize().intValue();
                        s1++;
                        System.out.println(s+"s");
                        System.out.println(s1+"s1");

                        if (s1 > 1 && j + 1 < processList.size() && (!(processList.get(j + 1).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0))) {
                            loadToCondensations(new Condensation("C"+count1++, BigInteger.valueOf(s),processList.get(j-1).getProcess().getSize(),processList.get(j).getProcess().getSize().add(processList.get(j-1).getProcess().getSize())));
                            count = 0;
                            s1 = 0;
                            s = 0;
                        }
                        else if(j + 1 == processList.size() && s1 > 1){
                            loadToCondensations(new Condensation("C"+count1++, BigInteger.valueOf(s),processList.get(j-1).getProcess().getSize(),processList.get(j).getProcess().getSize().add(processList.get(j-1).getProcess().getSize())));
                            count = 0;
                            s1 = 0;
                            s = 0;
                        }
                    }else {
                        s=0;
                        s1=0;
                    }
                }
                //count1++;
                s=0;
                count =0;
            }
        }
        spaceList.addAll(processList);
        for (int i = 0; i < spaceList.size(); i++) {
            if(spaceList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(0))==0){
                spaceList.get(i).setProcessList(new Process("Hueco", processList.get(i).getProcess().getTime(), processList.get(i).getProcess().getSize()));
            }
        }
        System.out.println("Condensations: ");
        System.out.println(condensations.toString());

        System.out.println("Huecos: ");
       // System.out.println(spaceList.toString());
    }

    public void iniSpace(){
        for (int i = 0; i < inQueue.size(); i++) {
            processList.add(new PartitionReport("part"+String.valueOf(i+1), inQueue.get(i)));
        }
    }
    public void cleanQueueList(){
        inQueue.clear();
    }

    public void cleanNewQueueList(){
        newInqueue.clear();
    }

}
