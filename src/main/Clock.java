package main;

import java.util.ArrayList;
import java.util.Iterator;

import gui.DataLoader;
import kernel.Adder;
import kernel.MemAccesser;
import kernel.FP;
import kernel.InstructionQueue;
import kernel.FakeMemory;
import kernel.Multiplier;
import kernel.ReserveStackEntry;
import kernel.FP.REG;
import util.ConstDefinition;
import util.Instr;

public class Clock {
	public static Adder adder;
	public static Multiplier multiplier;
	public static MemAccesser memAccesser;
	public static FP fp;
	public static InstructionQueue queue;
	public static FakeMemory mem;
	// 保留站组
	public static ReserveStackEntry[] addGroup, mulGroup;
	public static ReserveStackEntry[] memGroup;
	public static Double CDB_DATA;
	public static ArrayList<Instr> running_state = new ArrayList<Instr>();
	public static void sim_init() {
		fp = FP.getInstance();
		queue = new InstructionQueue();
		mem = new FakeMemory();
		addGroup = ReserveStackEntry.initGroup(ConstDefinition.ADD_RESERVE_ENTRY_NUM, "Adder");
		mulGroup = ReserveStackEntry.initGroup(ConstDefinition.MUL_RESERVE_ENTRY_NUM, "Multiplier");
		memGroup = ReserveStackEntry.initGroup(ConstDefinition.MEM_ACCESS_RESERVE_ENTRY_NUM, "MemAccesser");

		adder = new Adder(addGroup);
		multiplier = new Multiplier(mulGroup);
		memAccesser = new MemAccesser(memGroup);
	}
	
	public static void wake_up(ReserveStackEntry rse, Double ans) {
		/* 将保留站计算的结果放入总线 */
		CDB_DATA = ans;
		/* 唤醒其他等待计算结果的保留站更新数据 */
		ReserveStackEntry.listen(addGroup, rse);
		ReserveStackEntry.listen(mulGroup, rse);
		ReserveStackEntry.listen(memGroup, rse);
		/* 唤醒等待被写入的寄存器更新数据 */
		FP.listen(fp, rse);
		/* 清空总线数据 */
		CDB_DATA = null;
	}
	
	public static void print_reserver_state() {
		ReserveStackEntry.print(addGroup);
		ReserveStackEntry.print(mulGroup);
		ReserveStackEntry.print(memGroup);
	}
	public static void print_pipeline_state() {
		adder.print();
	}
	public static void print_fp_state() {
		FP.print(fp);
	}
	
	public static ArrayList<ArrayList<String>> get_running_state() {
		ArrayList<ArrayList<String>> states = new ArrayList<ArrayList<String>>();
		Iterator<Instr> it = running_state.iterator();
		while (it.hasNext()) {
			Instr itr = it.next();
			if (itr == null) continue;
			
			ArrayList<String> info = new ArrayList<String>();
			info.add(itr.toString());
			info.add("" + itr.state.flow);
			info.add("" + itr.state.running);
			info.add("" + itr.state.write_back);
			states.add(info);
			
			if (itr.state.mark) it.remove();
		}
		return states;
	}
	public static ArrayList<ArrayList<String>> get_instr_queue() {
		return queue.get_instr_queue();
	}
	public static ArrayList<ArrayList<String>> get_fake_memory(int begin) {
		return mem.get(begin, begin + 5);
	}
	public static ArrayList<ArrayList<String>> get_fp() {
		return fp.get_fp();
	}
	public static ArrayList<ArrayList<String>> get_reserve_station() {
		ArrayList<ArrayList<String>> reserve_station = new ArrayList<ArrayList<String>>();
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(addGroup, adder.getTime()));
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(mulGroup, multiplier.getTime()));
		reserve_station.addAll(ReserveStackEntry.get_reserved_entrys(memGroup, memAccesser.getTime()));
		return reserve_station;
	}
	
	private static boolean flag = true;
	private static int clock = 0;
	private static int clock_max = 1000;
	private static long timeout = 0;
	private static int step = 100;
	
	public static int get_clock_max() {
		return clock_max;
	}
	
	
	public static long get_timeout() {
		return timeout;
	}
	public static void run() {		
		for (int i = 0; i < step; ++i)
			run_one_step();
	}
	public static void setTimeOut(long _timeout) {
		if (_timeout <= 0) return ;
		timeout = _timeout;
	}
	
	public static void setStep(int _step) {
		if (_step <= 0) return;
		step = _step;
	}
	
	public static int getStep() {
		return step;
	}
	
	public static void setMaxCycle(int max) {
		if (max <= 0) return ;
		clock_max = max;
	}
	public static void run_one_step() {
		clock ++;
		queue.activate();
		adder.activate();
		multiplier.activate();
		memAccesser.activate();
		DataLoader.update_all(MainDriver.window.addr_mem);
	}
	public static void stop() {
		flag = !flag;
	}
	public static void clear() {
		ReserveStackEntry.clear(addGroup);
		ReserveStackEntry.clear(mulGroup);
		ReserveStackEntry.clear(memGroup);
		queue.clear();
		adder.clear();
		multiplier.clear();
		memAccesser.clear();
		FP.clear(fp);
		mem.clear();
		running_state.clear();
		clock = 0;
		clock_max = 1000;
		timeout = 0;
		flag = true;
	}
	public static int get_clock() {
		return clock;
	}
	
	public static void main(String[] args) {
		Clock.sim_init();

		fp.set(REG.F1, 7.0);
		fp.set(REG.F2, 6.2);
		fp.set(REG.F3, 2.5);
		fp.set(REG.F5, 3.0);
		fp.set(REG.F6, 7.6);
		fp.set(REG.F7, 4.8);
		fp.set(REG.F8, 1.1);
		fp.set(REG.F9, 1.3);
		ArrayList<String> instrs = new ArrayList<String>();
		instrs.add("ADD F1, F2, F6");
		instrs.add("SUB F2, F4, F3");
		instrs.add("LOAD F4, 3");
		instrs.add("STOR F2, 0");
		instrs.add("STOR F5, 2");
		instrs.add("LOAD F5, 0");
		instrs.add("ADD F8, F9, F2");
		instrs.add("MUL F4, F8, F7");
		instrs.add("STOR F3, 1");
		instrs.add("LOAD F7, 1");
		
		queue.load(instrs);
		int cycle = 50;
		while (cycle-- > 0) {
			System.out.println("clock : " + cycle);
			queue.activate();
//			print_reserver_state();
			adder.activate();
//			print_pipeline_state();
			multiplier.activate();
			memAccesser.activate();
//			print_fp_state();
		}
		print_fp_state();
		
	}
}
