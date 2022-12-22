import SwiftUI
import shared
import KMMViewModelSwiftUI

struct TaskListScreen: View {
    
    @State private var isDialogShown = false
    @StateViewModel var testViewModel = TestViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                Text("ViewModelScope \(testViewModel.intViewModelScopeFlowNativeValue)")
                Text("CoroutinesScope \(testViewModel.intCoroutinesCopeFlowNativeValue)")
            }

        }
    }
}

struct TaskListScreen_Previews: PreviewProvider {
    static var previews: some View {
        TaskListScreen()
    }
}
