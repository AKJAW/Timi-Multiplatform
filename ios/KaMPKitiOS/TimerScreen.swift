import SwiftUI
import shared
import KMPNativeCoroutinesCombine
import Combine

class TimerScreenState: ObservableObject {
    
    private var timerViewModel: TimerViewModel
    
    // TODO is this cancelled automatically?
    private var cancellables = Set<AnyCancellable>()
    
    @Published
    var isActive: Bool
    @Published
    var timeText: String
    
    init(timerViewModel: TimerViewModel = KoinWrapper.get(type: TimerViewModel.self)) {
        self.timerViewModel = timerViewModel
        self.isActive = timerViewModel.isActiveNativeValue
        self.timeText = timerViewModel.timeStringNativeValue
        
        // TODO can be less verbous?
        createPublisher(for: timerViewModel.isActiveNative)
            .receive(on: RunLoop.main)
            .sink { completion in
                print("Received isActiveNative completion: \(completion)")
            } receiveValue: { value in
                self.isActive = value.boolValue
            }
            .store(in: &cancellables)
        
        createPublisher(for: timerViewModel.timeStringNative)
            .receive(on: RunLoop.main)
            .sink { completion in
                print("Received timeStringNative completion: \(completion)")
            } receiveValue: { value in
                self.timeText = value
            }
            .store(in: &cancellables)
    }
    
    func onIconClicked() {
        self.timerViewModel.toggle()
    }
}

struct TimerScreen: View {
    @ObservedObject
    var state = TimerScreenState()
    
    var body: some View {
        TimerScreenContent(
            isActive: state.isActive,
            timeText: state.timeText,
            onIconClicked: state.onIconClicked
        )
    }
}

private struct TimerScreenContent: View {
    var isActive: Bool
    var timeText: String
    var onIconClicked: () -> Void
    
    var body: some View {
        ZStack {
            VStack {
                Text(timeText).font(.system(size: 32))
                let icon = isActive ? "pause" : "play"
                
                Image(systemName: icon)
                    .onTapGesture {
                        onIconClicked()
                    }
                    .font(.system(size: 50))
                    .frame(width: 50, height: 50)
            }
        }
    }
}

struct TimerScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        TimerScreenContent(
            isActive: true,
            timeText: "00:00",
            onIconClicked: {}
        )
    }
}
