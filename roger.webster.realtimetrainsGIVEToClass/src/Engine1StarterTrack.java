import java.awt.geom.Point2D;

public class Engine1StarterTrack extends TrackPositions {
	ReedContacts ReedContacts;

	public Engine1StarterTrack(ReedContacts ReedContactsIN) {
		super();
		ReedContacts = ReedContactsIN;
		InitializeAllPoints();
	}

	public void ResetTrack() {
		Reset();
	}
	public void MoveToNextTrackPosition() {
		this.MoveToNextTrackPositionTP();
		if (this.GetCurrentIndex() == 8)
		{
			ReedContacts.tripASwitch(15);
		}
	}

	public TrackPoint2D GetNextPosition() {
		TrackPoint2D mynextPoint = GetCurrentTrackPosition();
		return (mynextPoint);
	}

	public void InitializeAllPoints() {
		SetTrackLocation(0, 299, 102);
		SetTrackLocation(1, 292, 114);
		SetTrackLocation(2, 287, 127);
		SetTrackLocation(3, 280, 138);
		SetTrackLocation(4, 275, 147);
		SetTrackLocation(5, 273, 153);
		// ----------------------------
		SetTrackLocation(6, 262, 168);
		SetTrackLocation(7, 250, 175);
		SetTrackLocation(8, 241, 178);
		SetTrackLocation(9, 233, 182);
		SetTrackLocation(10, 216, 188);	
		SetTrackLocation(11, 207, 191);

	}
}
