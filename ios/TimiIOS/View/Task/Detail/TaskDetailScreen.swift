import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

struct TaskDetailScreen: View {
    
    let task: Task
    
    var body: some View {
        Text("Detail for \(task.id)")
    }
}
