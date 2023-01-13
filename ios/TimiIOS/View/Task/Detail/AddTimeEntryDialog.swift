import SwiftUI
import shared

struct AddTimeEntryDialog: ViewModifier {

    @Binding var isShowing: Bool
    var addEntry: (_ hours: Int, _ minutes: Int) -> Void
    @State private var dateNow: Date = Date.now

    init(isShowing: Binding<Bool>, addEntry: @escaping (_ hours: Int, _ minutes: Int) -> Void) {
        _isShowing = isShowing
        self.addEntry = addEntry
    }

    func body(content: Content) -> some View {
        ZStack {
            content
            if isShowing {
                Rectangle().foregroundColor(Color.black.opacity(0.6))
                    .onTapGesture {
                        isShowing = false
                    }
                HStack {
                    DatePicker(selection: $dateNow, in: ...Date.now, displayedComponents: .hourAndMinute) {
                        Text("Enter your time")
                    }.environment(\.locale, Locale.init(identifier: "en_GB"))
                    HStack {
                        Button("Add", action: { onSubmit() })
                    }
                }
                .padding()
                .frame(width: 250)
                .onTapGesture {}
                .padding(8)
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .foregroundColor(Color.white)
                )
            }
        }
    }

    func onSubmit() {
        isShowing = false
        let calendar = Calendar.current
        let hours = calendar.component(.hour, from: dateNow)
        let minutes = calendar.component(.minute, from: dateNow)
        addEntry(hours, minutes)
    }
}
