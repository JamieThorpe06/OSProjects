public class Switch2Track extends TrackPositions {
	ReedContacts ReedContacts;
	public Switch2Track(ReedContacts ReedContactsIN) {
		super();
		ReedContacts = ReedContactsIN;
		InitializeAllPoints();
	}

	public void ResetAll() {
		Reset();
	}

	public  void MoveToNextTrackPosition() {
		this.MoveToNextTrackPositionTP();
		if (this.GetCurrentIndex() == 4)
		{
			ReedContacts.tripASwitch(9);
		}
	}
		
	public TrackPoint2D GetNextPosition() {
		TrackPoint2D mynextPoint = GetCurrentTrackPosition();
		
		return (mynextPoint);
	}
	public void Switch() {

		this.SetCurrentIndex(0);
	}
	public void InitializeAllPoints() {
		int x = 0;
		SetTrackLocation(x++, 295, 202);
		SetTrackLocation(x++, 284, 207);
		SetTrackLocation(x++, 257, 213);
		SetTrackLocation(x++, 238, 220);
		SetTrackLocation(x++, 217, 229);
	
	
	}
}