public class Engine2StarterTrack extends TrackPositions {
	ReedContacts ReedContacts;
	public Engine2StarterTrack(ReedContacts ReedContactsIN) {

		super();
		ReedContacts = ReedContactsIN;
		InitializeAllPoints();
	}

	
	public void ResetTrack() {
		Reset();
	}
	public  void MoveToNextTrackPosition() {
		this.MoveToNextTrackPositionTP();
		if (this.GetCurrentIndex() == 10)
		{
			ReedContacts.tripASwitch(15);
		}
	}
	public TrackPoint2D GetNextPosition() {
		TrackPoint2D mynextPoint = GetCurrentTrackPosition();
		return (mynextPoint);
	}

	public void InitializeAllPoints() {
		int x=0;
		SetTrackLocation(x++, 362, 102);
		SetTrackLocation(x++, 353, 114);
		SetTrackLocation(x++, 342, 125);
		SetTrackLocation(x++, 325, 136);
		SetTrackLocation(x++, 309, 144);
		SetTrackLocation(x++, 300, 150);
		SetTrackLocation(x++, 292, 155);
		SetTrackLocation(x++, 270, 164);
		// ----------------------------
		SetTrackLocation(x++, 262, 168);
		SetTrackLocation(x++, 250, 175);
		SetTrackLocation(x++, 241, 178);
		SetTrackLocation(x++, 233, 182);
		SetTrackLocation(x++, 216, 188);
		SetTrackLocation(x++, 207, 191);

	}
}
