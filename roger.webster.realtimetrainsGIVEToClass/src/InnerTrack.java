public class InnerTrack extends TrackPositions {
	ReedContacts ReedContacts;
	public InnerTrack(ReedContacts ReedContactsIN) {
		super();
		ReedContacts = ReedContactsIN;
		InitializeAllPoints();
	}

	public void ResetTrack() {
		Reset();
	}
	public  void MoveToNextTrackPosition() {
		this.MoveToNextTrackPositionTP();
		//System.out.println("index " + this.GetCurrentIndex());
		if (this.GetCurrentIndex() == 9)
		{
			ReedContacts.tripASwitch(6);
		}
		if (this.GetCurrentIndex() == 21)
		{
			ReedContacts.tripASwitch(5);
		}
		if (this.GetCurrentIndex() == 33)
		{
			ReedContacts.tripASwitch(4);
		}
		if (this.GetCurrentIndex() == 49)
		{
			ReedContacts.tripASwitch(12);
		}
		if (this.GetCurrentIndex() == 58)
		{
			ReedContacts.tripASwitch(11);
		}
		if (this.GetCurrentIndex() == 76)
		{
			ReedContacts.tripASwitch(10);
		}
		
	}
	public TrackPoint2D GetNextPosition() {
		TrackPoint2D mynextPoint = GetCurrentTrackPosition();
	
		return (mynextPoint);
	}
	
	
	public void Switch() {

		this.SetCurrentIndex(37);
	}

	public boolean AtSwitchStation() {
		if (GetCurrentIndex() == 66)
			return (true);
		else
			return (false);
	}

	public void InitializeAllPoints() {
		int x = 0;
		SetTrackLocation(x++, 183, 198);
		SetTrackLocation(x++, 173, 198);
		SetTrackLocation(x++, 163, 198);
		SetTrackLocation(x++, 153, 198);
		SetTrackLocation(x++, 143, 198);
		SetTrackLocation(x++, 133, 198);
		SetTrackLocation(x++, 123, 198);
		SetTrackLocation(x++, 113, 198);
		SetTrackLocation(x++, 103, 198);
		// ----------------------------
		// curve
		// -----------------------------
		SetTrackLocation(x++, 94, 195);
		//ReedContacts.tripASwitch(6);
		SetTrackLocation(x++, 78, 183);
		SetTrackLocation(x++, 74, 176);
		SetTrackLocation(x++, 64, 162);
		SetTrackLocation(x++, 63, 157);
		SetTrackLocation(x++, 59, 144);
		SetTrackLocation(x++, 60, 124);
		SetTrackLocation(x++, 62, 117);
		SetTrackLocation(x++, 64, 107);
		SetTrackLocation(x++, 68, 98);
		SetTrackLocation(x++, 76, 89);
		SetTrackLocation(x++, 90, 75);
		//ReedContacts.tripASwitch(5);

		// ---------------------------------
		// long straight
		// ---------------------------------
		SetTrackLocation(x++, 103, 70);
		SetTrackLocation(x++, 113, 70);
		SetTrackLocation(x++, 123, 70);
		SetTrackLocation(x++, 133, 70);
		SetTrackLocation(x++, 143, 70);
		SetTrackLocation(x++, 153, 70);
		SetTrackLocation(x++, 163, 70);
		//ReedContacts.tripASwitch(4);
		SetTrackLocation(x++, 173, 70);
		SetTrackLocation(x++, 183, 70);
		SetTrackLocation(x++, 193, 70);
		SetTrackLocation(x++, 203, 70);
		SetTrackLocation(x++, 213, 70);
		SetTrackLocation(x++, 223, 70);
		SetTrackLocation(x++, 233, 70);
		SetTrackLocation(x++, 263, 70);
		SetTrackLocation(x++, 273, 70);
		SetTrackLocation(x++, 283, 70);
		SetTrackLocation(x++, 293, 70);
		SetTrackLocation(x++, 303, 70);
		SetTrackLocation(x++, 313, 70);
		SetTrackLocation(x++, 323, 70);
		SetTrackLocation(x++, 333, 70);
		SetTrackLocation(x++, 343, 70);
		SetTrackLocation(x++, 353, 70);
		SetTrackLocation(x++, 363, 70);
		SetTrackLocation(x++, 373, 70);
		SetTrackLocation(x++, 383, 70);
		// ----------------------------
		// curve
		// -----------------------------
		SetTrackLocation(x++, 410, 80);
		SetTrackLocation(x++, 427, 94);
		SetTrackLocation(x++, 431, 99);
	//	ReedContacts.tripASwitch(12);
		SetTrackLocation(x++, 437, 115);
		SetTrackLocation(x++, 441, 141);// mid
		SetTrackLocation(x++, 439, 154);
		SetTrackLocation(x++, 435, 166);
		SetTrackLocation(x++, 427, 180);
		SetTrackLocation(x++, 410, 197);

		// ---------------------------------
		// long straight
		// ---------------------------------
		SetTrackLocation(x++, 403, 198);
	//	ReedContacts.tripASwitch(11);
		SetTrackLocation(x++, 393, 198);
		SetTrackLocation(x++, 383, 198);
		SetTrackLocation(x++, 373, 198);
		SetTrackLocation(x++, 363, 198);
		SetTrackLocation(x++, 353, 198);
		SetTrackLocation(x++, 343, 198);
		SetTrackLocation(x++, 333, 198);
		SetTrackLocation(x++, 323, 198);
		SetTrackLocation(x++, 313, 198);
		SetTrackLocation(x++, 303, 198);
		SetTrackLocation(x++, 293, 198);
		SetTrackLocation(x++, 293, 198);
		SetTrackLocation(x++, 283, 198);
		SetTrackLocation(x++, 273, 198);
		SetTrackLocation(x++, 263, 198);
		SetTrackLocation(x++, 253, 198);
		SetTrackLocation(x++, 243, 198);
		SetTrackLocation(x++, 233, 198);
		SetTrackLocation(x++, 213, 198);
		SetTrackLocation(x++, 203, 198);
	//	ReedContacts.tripASwitch(10);
		SetTrackLocation(x++, 193, 198);

	}

}
