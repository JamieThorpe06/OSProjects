//package realtimetrains;

public class TrainImagePosition {
	
	private ReedContacts ReedContacts;// 
	private Engine1StarterTrack Engine1trackPosStart;// 
	private Engine2StarterTrack Engine2trackPosStart;// 
	private InnerTrack EnginetrackPosInner;// 
	private OutterTrack EnginetrackPosOutter;//
	private Switch2Track EngineSwitch2Pos;// 
	private Switch4Track EngineSwitch4Pos;// 

	private Boolean StarterTrack = true;
	private Boolean InnerTrack = false;
	private Boolean OutterTrack = false;
	private Boolean Switch2Track = false;
	private Boolean Switch4Track = false;

	private boolean Switch2 = false;// straight to start
	private boolean Switch4 = false;// straight to start
 
	private int TrainNumber = 0;

	public TrainImagePosition(int trainnumberIN, ReedContacts ReedContactsIN) {
		TrainNumber = trainnumberIN;
		ReedContacts = ReedContactsIN;
		Engine1trackPosStart = new Engine1StarterTrack(ReedContacts);
		Engine2trackPosStart = new Engine2StarterTrack(ReedContacts);
		EnginetrackPosInner = new InnerTrack(ReedContacts);
		EnginetrackPosOutter = new OutterTrack(ReedContacts);
		EngineSwitch2Pos = new Switch2Track(ReedContacts);
		EngineSwitch4Pos = new Switch4Track(ReedContacts);

	}
	public Boolean readASwitch(int index)
	{		
		return(ReedContacts.readASwitch(index));
	}
	public void ResetAll() {
		StarterTrack = true;
		InnerTrack = false;
		OutterTrack = false;
		Switch2Track = false;
		Switch4Track = false;
		
		if (TrainNumber == 0) {
			Engine1trackPosStart.ResetTrack();
			Engine1trackPosStart.ResetLoopCounter();
		}
		if (TrainNumber == 1) {
			Engine2trackPosStart.ResetTrack();
			Engine2trackPosStart.ResetLoopCounter();
		}
		EngineSwitch2Pos.Switch();
		EngineSwitch2Pos.ResetLoopCounter();

		EnginetrackPosInner.ResetTrack();
		EnginetrackPosOutter.ResetTrack();
		
		EngineSwitch4Pos.Switch();
		EngineSwitch4Pos.ResetLoopCounter();

	}

	public TrackPoint2D GetCurrentTrainPosition() {
		TrackPoint2D mynextpoint = new TrackPoint2D();
		if (StarterTrack) {
			if (TrainNumber == 0) {
				mynextpoint = Engine1trackPosStart.GetCurrentTrackPosition();
			} else {
				mynextpoint = Engine2trackPosStart.GetCurrentTrackPosition();
			}
		}
		if (Switch2Track) {
			mynextpoint = EngineSwitch2Pos.GetCurrentTrackPosition();
		}
		if (InnerTrack) {
			mynextpoint = EnginetrackPosInner.GetCurrentTrackPosition();
		}
		if (Switch4Track) {
			mynextpoint = EngineSwitch4Pos.GetCurrentTrackPosition();
		}
		if (OutterTrack) {
			mynextpoint = EnginetrackPosOutter.GetCurrentTrackPosition();
		}
		return (mynextpoint);
	}

	public void SetSwitch2(boolean flag) {
		Switch2 = flag;
	}

	public void SetSwitch4(boolean flag) {
		Switch4 = flag;
	}

	public void MoveTrainPosition() {
		if (StarterTrack) {
			if (TrainNumber == 0) {
				Engine1trackPosStart.MoveToNextTrackPosition();
				if (Engine1trackPosStart.GetLoopCounter() > 0) {
					StarterTrack = false;
					InnerTrack = true;
				}
			}
			if (TrainNumber == 1) {
				Engine2trackPosStart.MoveToNextTrackPosition();
				if (Engine2trackPosStart.GetLoopCounter() > 0) {
					StarterTrack = false;
					InnerTrack = true;
				}
			}
		}

		if (InnerTrack) {
			EnginetrackPosInner.MoveToNextTrackPosition();
			// EnginetrackPosInner.PrintCurrentTrackPosition();
			if (EnginetrackPosInner.AtSwitchStation()) {
				if (Switch2) {
					InnerTrack = false;
					OutterTrack = false;
					Switch2Track = true;
					EngineSwitch2Pos.Switch();
					EngineSwitch2Pos.ResetLoopCounter();

				}
			}
		}

		if (Switch2Track) {
			EngineSwitch2Pos.MoveToNextTrackPosition();
			// EnginetrackPosInner.PrintCurrentTrackPosition();
			if (EngineSwitch2Pos.GetLoopCounter() > 0) {
				EngineSwitch2Pos.ResetLoopCounter();
				Switch2Track = false;
				InnerTrack = false;
				OutterTrack = true;
				EnginetrackPosOutter.Switch();
			}
		}

		if (OutterTrack) {
			EnginetrackPosOutter.MoveToNextTrackPosition();
			// EnginetrackPosOutter.PrintCurrentTrackPosition();
			// switch
			if (EnginetrackPosOutter.AtSwitchStation()) {
				if (Switch4) {
					InnerTrack = false;
					Switch4Track = true;
					OutterTrack = false;
					EngineSwitch4Pos.Switch();
					EngineSwitch4Pos.ResetLoopCounter();
				}
			}

		}

		if (Switch4Track) {
			EngineSwitch4Pos.MoveToNextTrackPosition();
			// EnginetrackPosInner.PrintCurrentTrackPosition();
			if (EngineSwitch4Pos.GetLoopCounter() > 0) {
				EngineSwitch4Pos.ResetLoopCounter();
				Switch4Track = false;
				InnerTrack = true;
				OutterTrack = false;
				EnginetrackPosInner.Switch();
			}
		}

	}
}