package no.kristiania.pgr200.database;

public class Program {
			
	public ConferenceTalk parseCommand(String[] args) {
		ConferenceTalk talk = new ConferenceTalk();
		
		for(int i = 1; i < args.length-1; i += 2) {
			switch(args[i]) {
			case "-title":
				talk.setTitle(args[i+1]);
				break;
			case "-description":
				talk.setDescription(args[i+1]);
				break;
			default:
				break;
			
			}
		}

		return talk;
		
	}

}
