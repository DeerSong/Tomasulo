package kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import gui.DataLoader;
import main.Clock;
import util.FileReaderUtil;
//import util.FileReaderUtil;
import util.Instr;

/*
 * 指令队列
 */
public class InstructionQueue {
	Queue<Instr> itrsQue = new LinkedList<Instr>();
	Instr crItr = null;
	
	public InstructionQueue() {}
	
	public ArrayList<ArrayList<String>> get_instr_queue() {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
//		System.out.println(itrsQue.size());
		Iterator<Instr> it = itrsQue.iterator();
		while (it.hasNext()) {
			Instr itr = it.next();
			if (itr == null) continue;
			list.add(itr.get_list());
		}
		return list;
	}
	
	public void clear() {
		itrsQue.clear();
	}
	
	public boolean load(String fileName) {
		ArrayList<String> instrs = new ArrayList<String>();
		FileReaderUtil.readFileToList(fileName, instrs);
		return load(instrs);
	}
	public boolean load(ArrayList<String> instrs) {
		for (String instr : instrs) {
			itrsQue.add(decodeInstr(instr));
		}
		return true;
	}
	
	
	public void activate() {
		transfer();
	}
	
	private void transfer() {
		if (crItr == null) crItr = itrsQue.poll();
		if (crItr == null) return ;
		
		ReserveStackEntry reserveStackEntry = null;
		switch (crItr.op) {
		case ADDD:
		case SUBD: reserveStackEntry = ReserveStackEntry.getFreeEntry(Clock.addGroup);
			break;
		case MULD:
		case DIVD: reserveStackEntry = ReserveStackEntry.getFreeEntry(Clock.mulGroup);
			break;
		case LD:
		case ST:
			reserveStackEntry = ReserveStackEntry.getFreeEntry(Clock.memGroup);
			break;
//		case LOAD: reserveStackEntry = ReserveStackEntry.getFreeEntry(Clock.loadGroup);
//			break;
//		case STOR: reserveStackEntry = ReserveStackEntry.getFreeEntry(Clock.storeGroup);
//			break;
		}
		if (reserveStackEntry == null) return ;
		
		ReserveStackEntry.setReserveEntry(reserveStackEntry, crItr);
		crItr = null;
	}
	
	/*
	 * 调试主函数，
	 * 运行本函数，能够测试指令队列能否正确对各种指令解码。
	 */
	public static void main(String[] args) {
		InstructionQueue iq = new InstructionQueue();
		Instr is;
		
		is = iq.decodeInstr("LD F6, 34  ");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("  SUBD F6, F5,  F4, GET");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("DIVD F6, F7,F4");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("ST	 F6, 34  ");
		if (is != null)
			System.out.println(is.toString());
		
		is = iq.decodeInstr("LD F6, 34  ");
		if (is != null)
			System.out.println(is.toString());
	}

	private Instr decodeInstr(String instr) {
		String[] infos = instr.split("[\t ,()]");
//		System.out.println(infos);

		String op = null, left = null, mid = null, right = null;
		try {
			int cc = 0;
			while ((op = infos[cc ++]).equals(""));
			while ((left = infos[cc ++]).equals(""));
			while ((mid = infos[cc ++]).equals(""));
			if (cc < infos.length) {
				while ((right = infos[cc ++]).equals(""));
			}
			
			if (cc < infos.length) throw new Exception();
		}
		catch (Exception e) {
			System.out.println(">>> Error at decode instruction : \n\t" + instr);
			DataLoader.update_console(">>> Error at decode instruction : \n\t" + instr);
			return null;
		}

		Instr istr = new Instr(instr);
		// 检查操作码
		try {
			istr.op = Instr.OP.valueOf(op);
			try {
				switch (istr.op) {
				case ADDD : 
				case SUBD : 
				case MULD : 
				case DIVD : 
					istr.des = FP.REG.valueOf(left);
					istr.src1 = FP.REG.valueOf(mid);
					istr.src2 = FP.REG.valueOf(right);
					break;
				case LD :
					istr.des = FP.REG.valueOf(left);
					istr.imm = Integer.parseInt(mid);
					break;
				case ST : 
					istr.src1 = FP.REG.valueOf(left);
					istr.imm = Integer.parseInt(mid);
					break;
				default:
					// should not goto here.
					assert(true);
				}
			} catch (IllegalArgumentException e) {
				System.out.println(">>> Error at decode reg or imm : \n\t" + instr);
				DataLoader.update_console(">>> Error at decode reg or imm : \n\t" + instr);
				return null;
			}
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(">>> Error at decode operator : \n\t" + instr);
			DataLoader.update_console(">>> Error at decode operator : \n\t" + instr);
			return null;
		}
		catch (Exception e) {
			System.out.println(">>> Unrecognized error : \n\t" + instr);
			DataLoader.update_console(">>> Unrecognized error : \n\t" + instr);
			return null;
		}
		
		return istr;
	}
}
