public class OutterTrack extends TrackPositions {
	ReedContacts ReedContacts;
	
	public OutterTrack(ReedContacts ReedContactsIN) {
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
		if (this.GetCurrentIndex() == 10)
		{
			ReedContacts.tripASwitch(7);
		}
		if (this.GetCurrentIndex() == 24)
		{
			ReedContacts.tripASwitch(1);
		}
		if (this.GetCurrentIndex() == 40)
		{
			ReedContacts.tripASwitch(2);
		}
		if (this.GetCurrentIndex() == 56)
		{
			ReedContacts.tripASwitch(13);
		}
		if (this.GetCurrentIndex() == 67)
		{
			ReedContacts.tripASwitch(14);
		}
		if (this.GetCurrentIndex() == 82)
		{
			ReedContacts.tripASwitch(8);
		}
		
	}
	public TrackPoint2D GetNextPosition() {
		TrackPoint2D mynextPoint = GetCurrentTrackPosition();
		return (mynextPoint);
	}

	public boolean AtSwitchStation() {
		if (GetCurrentIndex() == 35)
			return (true);
		else
			return (false);
	}

	public void Switch() {

		this.SetCurrentIndex(0);
	}

	public void InitializeAllPoints() {
		int x = 0;
		SetTrackLocation(x++, 203, 240);
		SetTrackLocation(x++, 193, 240);
		SetTrackLocation(x++, 183, 240);
		SetTrackLocation(x++, 173, 240);
		SetTrackLocation(x++, 163, 240);
		SetTrackLocation(x++, 153, 240);
		SetTrackLocation(x++, 143, 240);
		SetTrackLocation(x++, 133, 240);
		SetTrackLocation(x++, 123, 240);
		SetTrackLocation(x++, 113, 240);
		SetTrackLocation(x++, 103, 240);
		// ----------------------------
		// curve
		// -----------------------------
		SetTrackLocation(x++, 70, 230);
		SetTrackLocation(x++, 57, 220);
		//ReedContacts.tripASwitch(7);
		SetTrackLocation(x++, 47, 210);
		SetTrackLocation(x++, 35, 196);
		SetTrackLocation(x++, 26, 182);
		SetTrackLocation(x++, 16, 148);
		SetTrackLocation(x++, 15, 132);// mid
		SetTrackLocation(x++, 19, 112);
		SetTrackLocation(x++, 20, 105);
		SetTrackLocation(x++, 26, 86);
		SetTrackLocation(x++, 31, 78);
		SetTrackLocation(x++, 39, 64);
		SetTrackLocation(x++, 58, 44);
		
		// ---------------------------------
		// long straight
		// ---------------------------------
		SetTrackLocation(x++, 90, 31);
		//ReedContacts.tripASwitch(1);
		SetTrackLocation(x++, 100, 31);
		SetTrackLocation(x++, 110, 31);
		SetTrackLocation(x++, 120, 31);
		SetTrackLocation(x++, 130, 31);
		SetTrackLocation(x++, 140, 31);
		SetTrackLocation(x++, 150, 31);
		SetTrackLocation(x++, 160, 31);
		SetTrackLocation(x++, 170, 31);
		SetTrackLocation(x++, 180, 31);
		SetTrackLocation(x++, 190, 31);
		SetTrackLocation(x++, 200, 31);
		SetTrackLocation(x++, 210, 31);
		SetTrackLocation(x++, 220, 31);
		SetTrackLocation(x++, 230, 31);
		SetTrackLocation(x++, 240, 31);
		SetTrackLocation(x++, 250, 31);
		SetTrackLocation(x++, 260, 31);
		SetTrackLocation(x++, 270, 31);
	//	ReedContacts.tripASwitch(10);
		SetTrackLocation(x++, 280, 31);
		SetTrackLocation(x++, 300, 31);
		SetTrackLocation(x++, 300, 31);
		SetTrackLocation(x++, 310, 31);
		SetTrackLocation(x++, 320, 31);
		SetTrackLocation(x++, 330, 31);
		SetTrackLocation(x++, 340, 31);
		SetTrackLocation(x++, 350, 31);
		SetTrackLocation(x++, 360, 31);
		SetTrackLocation(x++, 370, 31);
		SetTrackLocation(x++, 380, 31);
		SetTrackLocation(x++, 390, 31);
		SetTrackLocation(x++, 400, 31);
	//	ReedContacts.tripASwitch(13);
		// ----------------------------
		// curve
		// -----------------------------
		SetTrackLocation(x++, 423, 36);
		SetTrackLocation(x++, 447, 54);
		SetTrackLocation(x++, 466, 75);
		SetTrackLocation(x++, 477, 98);
		SetTrackLocation(x++, 483, 123);
		SetTrackLocation(x++, 483, 132);// mid
		SetTrackLocation(x++, 482, 160);
		SetTrackLocation(x++, 478, 177);
		SetTrackLocation(x++, 469, 194);
		SetTrackLocation(x++, 459, 208);
		SetTrackLocation(x++, 443, 222);
//		ReedContacts.tripASwitch(14);
		// ---------------------------------
		// long straight
		// ---------------------------------
		SetTrackLocation(x++, 410, 240);
		SetTrackLocation(x++, 400, 240);
		SetTrackLocation(x++, 390, 240);
		SetTrackLocation(x++, 380, 240);
		SetTrackLocation(x++, 370, 240);
		SetTrackLocation(x++, 360, 240);
		SetTrackLocation(x++, 350, 240);
		SetTrackLocation(x++, 340, 240);
		SetTrackLocation(x++, 330, 240);
		SetTrackLocation(x++, 320, 240);
		SetTrackLocation(x++, 310, 240);
		SetTrackLocation(x++, 300, 240);
		SetTrackLocation(x++, 300, 240);
		SetTrackLocation(x++, 280, 240);
		SetTrackLocation(x++, 270, 240);
		SetTrackLocation(x++, 260, 240);
		SetTrackLocation(x++, 250, 240);
		SetTrackLocation(x++, 240, 240);
		SetTrackLocation(x++, 230, 240);
		SetTrackLocation(x++, 220, 240);
//		ReedContacts.tripASwitch(8);
		SetTrackLocation(x++, 210, 240);

	}

}
