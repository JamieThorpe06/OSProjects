
public class Switch4Track extends TrackPositions {
	ReedContacts ReedContacts;
	public Switch4Track(ReedContacts ReedContactsIN) {
		super();
		ReedContacts = ReedContactsIN;
		InitializeAllPoints();
	}

	public void ResetAll() {
		Reset();
	}
	public  void MoveToNextTrackPosition() {
		this.MoveToNextTrackPositionTP();
		if (this.GetCurrentIndex() == 3)
		{
			ReedContacts.tripASwitch(3);
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
		SetTrackLocation(x++, 213, 38);
		SetTrackLocation(x++, 234, 47);
		SetTrackLocation(x++, 252, 53);
		SetTrackLocation(x++, 268, 60);
		
	}

}
